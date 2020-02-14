package simulator.model;

public class CityRoad extends Road {
    CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
        super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
    }

    @Override
    void reduceTotalContamination() {
        int x;
        switch (getWeather()) {
            case WINDY:
                x = 10;
                break;
            case STORM:
                x = 15;
                break;
            default:
                x = 2;
        }

        if (_totalContamination >= x)
            _totalContamination -= x;
    }

    @Override
    void updateSpeedLimit() {
        // speed limit does not change and = max speed
    }

    @Override
    int calculateVehicleSpeed(Vehicle v) {
        return (int)( ((11.0 - v.getContClass()) / 11.0) * _limitSpeed);
    }
}
