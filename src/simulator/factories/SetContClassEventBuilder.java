package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContaminationClassEvent;

import java.util.ArrayList;
import java.util.List;

public class SetContClassEventBuilder extends Builder<Event> {

    public SetContClassEventBuilder() {
        super("set_cont_class");
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        int time;
        List<Pair<String, Integer>> pairs;
        try {
            time = data.getInt("time");
            JSONArray info = data.getJSONArray("info");
            pairs = new ArrayList<>();
            for (int i = 0; i < info.length(); i++) {
                String first = info.getJSONObject(i).getString("vehicle");
                Integer second = info.getJSONObject(i).getInt("class");// Maybe getBigInt
                pairs.add(new Pair<String, Integer>(first, second));
            }
        }
        catch(NullPointerException | ClassCastException e) {
            System.out.println("The JSON object is incorrect" + e.getMessage());
            return null;
        }
        return new SetContaminationClassEvent(time, pairs);
    }
}
