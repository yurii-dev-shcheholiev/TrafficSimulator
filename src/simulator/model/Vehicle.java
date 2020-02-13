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
        _itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));;
        //TODO initialize other fields
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
            throw new IllegalArgumentException("Contamination class must be in range 1-10");
        _contamination = contClass;
    }

    void moveToNextRoad() {
        //TODO
    }

    @Override
    void advance(int time) {
        if (!_status.equals(VehicleStatus.TRAVELING))
            return;

        _location = Math.min(_location + _currentSpeed, _road.getLength());
//        _contamination = 1;
        if (_location == _road.getLength())
            _status = VehicleStatus.WAITING;
//            _itinerary;
        //TODO
    }

    @Override
    public JSONObject report() {
        //TODO
        return null;
    }
}
