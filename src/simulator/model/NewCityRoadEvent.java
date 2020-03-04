package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {

    public NewCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
        super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
    }


    @Override
    void execute(RoadMap map) {
        map.addRoad(new CityRoad(_id, map.getJunction(_srcJunction), map.getJunction(_destJunction), _maxSpeed, _contLimit, _length, _weather));
    }
}