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
            case STORM:
                x = 10;
                break;
            default:
                x = 2;
        }

        _totalContamination = Math.max(0, _totalContamination - x);
    }

    @Override
    void updateSpeedLimit() {
        // speed limit does not change and = max speed
        _limitSpeed = getMaxSpeed();
    }

    @Override
    int calculateVehicleSpeed(Vehicle v) {
        return (int) (((11.0 - v.getContClass()) / 11.0) * _limitSpeed);
    }
}
