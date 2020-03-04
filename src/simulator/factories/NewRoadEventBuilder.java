package simulator.factories;

import org.json.JSONObject;
import simulator.model.*;

public abstract class NewRoadEventBuilder extends Builder<Event> {
    protected int time;
    protected String id;
    protected String src;
    protected String dest;
    protected int length;
    protected int co2limit;
    protected int maxspeed;
    protected Weather weather;

    NewRoadEventBuilder(String type) {
        super(type);
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        try {
            time = (data.getInt("time"));
            id = data.getString("id");
            src = data.getString("src");
            dest = data.getString("dest");
            length = data.getInt("length");
            co2limit = data.getInt("co2limit");
            maxspeed = data.getInt("maxspeed");
            weather = Weather.valueOf(data.getString("weather").toUpperCase());
            }
            catch(NullPointerException | ClassCastException e) {
            System.out.println("The JSON object is incorrect" + e.getMessage());
            return null;
        }
        return createTheRoad();
    }

    abstract Event createTheRoad();
}
