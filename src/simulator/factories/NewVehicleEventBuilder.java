package simulator.factories;

import org.json.JSONObject;
import simulator.model.Event;
import simulator.model.NewVehicleEvent;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEventBuilder extends Builder<Event> {
    private int time;
    private String id;
    private int co2limit;
    private int maxspeed;
    private List<String> itinerary;

    NewVehicleEventBuilder() {
        super("new_vehicle");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        try {
            time = (data.getInt("time"));
            id = data.getString("id");
            co2limit = data.getInt("co2limit");
            maxspeed = data.getInt("maxspeed");
            itinerary = new ArrayList<>();
            for (int i = 0; i < data.getJSONArray("itinerary").length(); i++) {
                itinerary.add(data.getJSONArray("itinerary").getString(i));
            }
        }
        catch(NullPointerException | ClassCastException e) {
            System.out.println("The JSON object is incorrect" + e.getMessage());
            return null;
        }
        return new NewVehicleEvent(time, id, maxspeed, co2limit, itinerary);
    }
}
