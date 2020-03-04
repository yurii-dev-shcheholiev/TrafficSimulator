package simulator.factories;

import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.MoveAllStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveAllStrategyBuilder extends Builder<DequeuingStrategy> {

    MoveAllStrategyBuilder(String type) {
        super(type);
    }

    @Override
    protected DequeuingStrategy createTheInstance(JSONObject data) {
        if (data != null)
            return null;
        return new MoveAllStrategy();
    }
}
