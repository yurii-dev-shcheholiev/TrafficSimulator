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
        
        _road = null;
        _totalContamination = 0;
        _totalDistance = 0;
    }

    int getLocation() {
        return _location;
    }

    int getSpeed() {
        return _currentSpeed;
    }

    int getContClass() {
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

    void setSpeed(int speed) {
        if (speed < 0)
            throw new IllegalArgumentException("Speed must be not negative");
        _currentSpeed = Math.min(speed, _maxSpeed);
    }

    void setContClass(int contClass) {
        if (contClass < 0 || contClass > 10)
            throw new IllegalArgumentException("Contamination class must be in range 0-10");
        _contamination = contClass;
    }

    void moveToNextRoad() {
        //TODO
    }

    @Override
    void advance(int time) {
        if (!_status.equals(VehicleStatus.TRAVELING)) {
        	_currentSpeed = 0;
        	return;
        }
        
        int _previousLocation = _location;
        
        //(a)
        _location = Math.min(_previousLocation + _currentSpeed, _road.getLength());
        
        _totalDistance += _location;
        
        //(b)
        
        int c = ((_location - _previousLocation) * _contamination);
        _totalContamination += c;  
        
        //(c)
        if (_location == _road.getLength())
            _status = VehicleStatus.WAITING;
        	_currentSpeed = 0;
//            _itinerary;  JUNCTION
        
        //TODO
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
    	
    	
    	if (!(_status.equals(VehicleStatus.PENDING) || _status.equals(VehicleStatus.ARRIVED))) {
    		
    		ob.put("road", _road.getId());
    		ob.put("location", _location);
		}
    	
    	return ob;
    }
}
