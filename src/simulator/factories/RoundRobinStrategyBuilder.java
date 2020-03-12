package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.RoundRobinStrategy;

public class RoundRobinStrategyBuilder extends Builder<LightSwitchingStrategy>{

    public RoundRobinStrategyBuilder() {
        super("round_robin_lss");
    }

    @Override
    protected LightSwitchingStrategy createTheInstance(JSONObject data) {

        int timeslot = 1;
        try {
            if (data.length() != 0)
                timeslot = (data.getInt("timeslot"));
        } catch(NullPointerException | ClassCastException e) {
            System.out.println("The JSON object is incorrect" + e.getMessage());
            return null;
        }
        return new RoundRobinStrategy(timeslot);
    }
}
