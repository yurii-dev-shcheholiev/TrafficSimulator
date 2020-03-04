package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

    RoundRobinStrategyBuilder(String type) {
        super(type);
    }

    @Override
    protected LightSwitchingStrategy createTheInstance(JSONObject data) {
        int i = 0;
        try {
            i = (data.getInt("timeslot"));
        } catch(NullPointerException | ClassCastException e) {
            System.out.println("The JSON object is incorrect" + e.getMessage());
            return null;
        }
        return new RoundRobinStrategy(i);
    }
}
