package simulator.model;

import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vehicle extends SimulatedObject {

    private List<Junction> _itinerary;
    private int _maxSpeed;
    private int _currentSpeed;
    private VehicleStatus _status;
    private Road _road;
    private int _location;
    private int _contamination;
    private int _totalContamination;
    private int _totalDistance;
    private int _currentJ;

    Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
        super(id);
        if (maxSpeed < 0)
            throw new IllegalArgumentException("Max speed must be positive");
        if (contClass < 0 || contClass > 10)
            throw new IllegalArgumentException("Contamination class must be in range 1-10");
        if (itinerary.size() < 2)
            throw new IllegalArgumentException("Itinerary length must be at least 2");

        _maxSpeed = maxSpeed;
        _contamination = contClass;
        _itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));

        _status = VehicleStatus.PENDING;
        _road = null;
        _totalContamination = 0;
        _totalDistance = 0;
        _currentJ = 0;
    }

    public int getLocation() {
        return _location;
    }

    public int getSpeed() {
        return _currentSpeed;
    }

    public int getContClass() {
        return _contamination;
    }

    public VehicleStatus getStatus() {
        return _status;
    }

    public List<Junction> getItinerary() {
        return _itinerary;
    }

    public Road getRoad() {
        return _road;
    }

    public int getMaxSpeed(){ return _maxSpeed; }

    public int getTotalDistance(){ return _totalDistance; }

    public int currentJunction(){ return _currentJ; }

    public int getTotalContamination(){ return _totalContamination; }


    void setSpeed(int speed) {
        if (speed < 0)
            throw new IllegalArgumentException("Speed must be not negative");
        // TODO REVIEW
        if (_status == VehicleStatus.WAITING)
            _currentSpeed = 0;
        else
            _currentSpeed = Math.min(speed, _maxSpeed);
    }

    void setContClass(int contClass) {
        if (contClass < 0 || contClass > 10)
            throw new IllegalArgumentException("Contamination class must be in range 0-10");
        _contamination = contClass;
    }

    void moveToNextRoad() {
        if (_status != VehicleStatus.PENDING && _status != VehicleStatus.WAITING)
            throw new IllegalArgumentException("Vehicle status must be PENDING or WAITING");

        if (_road != null)
            _road.exit(this);

        if (_itinerary.size() - 1 == _currentJ) {
            _status = VehicleStatus.ARRIVED;
            _road = null;
            _location = 0;
            _currentSpeed = 0;
        } else {
            _road = _itinerary.get(_currentJ).roadTo(_itinerary.get(_currentJ+1));
            _location = 0;
            _currentSpeed = 0;
            _status = VehicleStatus.TRAVELING;
            _road.enter(this);
        }
    }

    @Override
    void advance(int time) {
        if (_status != VehicleStatus.TRAVELING) {
        	return;
        }
        
        int previousLocation = _location;
        
        //(a)
        _location = Math.min(previousLocation + _currentSpeed, _road.getLength());
        _totalDistance += (_location - previousLocation);
        //(b)
        int c = ((_location - previousLocation) * _contamination);
        _totalContamination += c;  
        _road.addContamination(c);
        //(c)
        if (_location == _road.getLength()) {
            _status = VehicleStatus.WAITING;
            _currentSpeed = 0;

            _currentJ++;
            _road.getDestJunction().enter(this);
        }
    }

    @Override
    public JSONObject report() {
     
    	JSONObject ob = new JSONObject();
    	ob.put("id", _id);
    	ob.put("speed", _currentSpeed);
    	ob.put("distance", _totalDistance);
    	ob.put("co2", _totalContamination);
    	ob.put("class", _contamination);
    	ob.put("status", _status);

    	if (_status != VehicleStatus.PENDING && _status != VehicleStatus.ARRIVED) {
    		ob.put("road", _road.getId());
    		ob.put("location", _location);
		}
    	
    	return ob;
    }
}
