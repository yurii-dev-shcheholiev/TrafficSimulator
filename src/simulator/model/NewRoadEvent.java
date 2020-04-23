package simulator.model;

public abstract class NewRoadEvent extends Event {

    protected String _id;
    protected String  _srcJunction;
    protected String  _destJunction;
    protected int _length;
    protected int _contLimit;
    protected int _maxSpeed;
    protected Weather _weather;

    public NewRoadEvent(int time, String id, String  srcJun, String  destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
        super(time);

        _id = id;
        _srcJunction = srcJun;
        _destJunction = destJunc;
        _length = length;
        _contLimit = co2Limit;
        _maxSpeed = maxSpeed;
        _weather = weather;
    }
}
