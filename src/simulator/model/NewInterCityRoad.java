package simulator.model;

public class NewInterCityRoad extends NewRoadEvent {

    public NewInterCityRoad(int time, String id, Junction srcJun, Junction destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
        super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
    }


    @Override
    void execute(RoadMap map) {
        map.addRoad(new InterCityRoad(_id, _srcJunction, _destJunction, _maxSpeed, _contLimit, _length, _weather));
    }

}
