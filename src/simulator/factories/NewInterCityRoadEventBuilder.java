package simulator.factories;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;

public class NewInterCityRoadEventBuilder extends NewRoadEventBuilder {
    NewInterCityRoadEventBuilder(String type) {
        super(type);
    }

    @Override
    Event createTheRoad() {
        return new NewInterCityRoadEvent(time, id, src, dest, length, co2limit, maxspeed, weather);
    }
}
