package simulator.factories;

import org.json.JSONObject;
import simulator.model.LightSwitchingStrategy;
import simulator.model.MostCrowdedStrategy;
import simulator.model.RoundRobinStrategy;

public class MostCrowdedStrategyBuilder extends Builder<LightSwitchingStrategy>{
    public MostCrowdedStrategyBuilder() {
        super("most_crowded_lss");
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
        return new MostCrowdedStrategy(i);
    }
}
