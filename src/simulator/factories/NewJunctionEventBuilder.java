package simulator.factories;

import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event> {
    private Factory<LightSwitchingStrategy> _fls;
    private Factory<DequeuingStrategy> _fdq;

    public NewJunctionEventBuilder(Factory<LightSwitchingStrategy>lssFactory, Factory<DequeuingStrategy> dqsFactory) {
        super("new_junction");
        _fls = lssFactory;
        _fdq = dqsFactory;
    }

    @Override
    protected Event createTheInstance(JSONObject data) {
        int time = 0;
        String id = "";
        int x;
        int y;
        DequeuingStrategy dq;
        LightSwitchingStrategy ls;
        try {
            time = (data.getInt("time"));
            id = data.getString("id");
            x = data.getJSONArray("coor").getInt(0);
            y = data.getJSONArray("coor").getInt(1);
            ls = _fls.createInstance(data.getJSONObject("ls_strategy"));
            dq = _fdq.createInstance(data.getJSONObject("dq_strategy"));
        } catch(NullPointerException | ClassCastException e) {
            System.out.println("The JSON object is incorrect" + e.getMessage());
            return null;
        }
        return new NewJunctionEvent(time, id, ls, dq, x, y);
    }
}
