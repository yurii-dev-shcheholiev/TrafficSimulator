package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

import java.util.ArrayList;
import java.util.List;

public class SetWeatherEventBuilder extends Builder<Event> {

    public SetWeatherEventBuilder() {
        super("set_weather");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        int time;
        List<Pair<String, Weather>> pairs;
        try {
            time = data.getInt("time");
            JSONArray info = data.getJSONArray("info");
            pairs = new ArrayList<>();
            for (int i = 0; i < info.length(); i++) {
                String first = info.getJSONObject(i).getString("road");
                Weather second = Weather.valueOf(info.getJSONObject(i).getString("weather"));
                pairs.add(new Pair<String, Weather>(first, second));
            }
        }
        catch(NullPointerException | ClassCastException e) {
            System.err.println("The JSON object is incorrect" + e.getMessage());
            return null;
        }
        return new SetWeatherEvent(time, pairs);
    }
}
