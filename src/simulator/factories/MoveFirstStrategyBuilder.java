package simulator.factories;

import org.json.JSONObject;
import simulator.model.DequeuingStrategy;
import simulator.model.MoveFirstStrategy;

public class MoveFirstStrategyBuilder extends Builder<DequeuingStrategy> {

    MoveFirstStrategyBuilder(String type) {
        super(type);
    }

    @Override
    protected DequeuingStrategy createTheInstance(JSONObject data) {
        if (data != null)
            return null;
        return new MoveFirstStrategy();
    }
}
