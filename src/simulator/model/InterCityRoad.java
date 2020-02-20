package simulator.model;

public class InterCityRoad extends Road {

    InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
    void reduceTotalContamination() {
        int x;
        switch (this.getWeather()) {
            case SUNNY:
                x = 2;
                break;
            case CLOUDY:
                x = 3;
                break;
            case RAINY:
                x = 10;
                break;
            case WINDY:
                x = 15;
                break;
            case STORM:
                x = 20;
                break;
            default:
                x = 0;
        }
        _totalContamination = (int)( ((100 - x) / 100) * _totalContamination);
    }

    @Override
    void updateSpeedLimit() {
        if (_totalContamination > getContLimit()) {
            _limitSpeed = (int)(getMaxSpeed() * 0.5);
        } else
            _limitSpeed = getMaxSpeed();
    }

    @Override
    int calculateVehicleSpeed(Vehicle v) {
        if (getWeather() == Weather.STORM)
            return (int)( _limitSpeed * 0.8);
        else
            return _limitSpeed;
    }
}
