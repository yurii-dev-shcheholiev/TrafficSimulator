package simulator.model;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

import java.util.ArrayList;
import java.util.List;

public abstract class Road extends SimulatedObject{
    private Junction _srcJunction;
    private Junction _destJunction;
    private int _length;
    private int _maxSpeed;
    private int _limitSpeed;
    private int _contLimit;
    private Weather _weather;
    private int _totalContamination;
    //should always be sorted by vehicle location
    private List<Vehicle> _vehicles;

    Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id);
        if (maxSpeed < 0)
            throw new IllegalArgumentException("Max speed must be positive");
        if (contLimit < 0)
            throw new IllegalArgumentException("Contamination limit must be non-negative");
        if (length <= 0)
            throw new IllegalArgumentException("Length must be positive");
        if (srcJunc == null)
            throw new IllegalArgumentException("Source junctions must not be null");
        if (destJunc == null)
            throw new IllegalArgumentException("Destination junctions must not be null");
        if (weather == null)
            throw new IllegalArgumentException("Weather must not be null");

        // TODO modify Junctions in constructor
        _srcJunction = srcJunc;
        _destJunction = destJunc;
        _maxSpeed = maxSpeed;
        _contLimit = contLimit;
        _length = length;
        _weather = weather;

        _limitSpeed = maxSpeed;
        _totalContamination = 0;
        _vehicles = new ArrayList<Vehicle>();
    }

    public int getLength() {
        return _length;
    }

    public Junction getSrcJunction() {
        return _srcJunction;
    }

    public Junction getDestJunction() {
        return _destJunction;
    }

    void setWeather(Weather weather) {
        if (weather == null)
            throw new IllegalArgumentException("Weather must not be null");
        _weather = weather;
    }

    void addContamination(int cont) {
        if (cont < 0)
            throw new IllegalArgumentException("Contamination must be non-negative");
        _totalContamination += cont;
    }

    void enter(Vehicle v) {
        if (v.getLocation() != 0 || v.getSpeed() != 0)
            throw new IllegalArgumentException("Vehicle must locate at 0 and have speed 0");
        _vehicles.add(v);
    }

    void exit(Vehicle v) {
        _vehicles.remove(v);
    }


    @Override
    void advance(int time) {
        reduceTotalContamination();
        updateSpeedLimit();

        // TODO if it is correct
        for (Vehicle vehicle : _vehicles) {
            vehicle.setSpeed(calculateVehicleSpeed(vehicle));
            vehicle.advance(time);
        }
        // TODO recall vehicle sorting
    }

    @Override
    public JSONObject report() {
        return null;
    }

    abstract void reduceTotalContamination();
    abstract void updateSpeedLimit();
    abstract int calculateVehicleSpeed(Vehicle v);
}
