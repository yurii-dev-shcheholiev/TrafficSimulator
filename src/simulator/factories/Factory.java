package simulator.factories;

import org.json.JSONObject;

public interface Factory<T> {
	public T createInstance(JSONObject info);
}

// TODO
//	List<Builder<LightSwitchingStrategy>> lsbs =newArrayList<>();
//	lsbs.add(newRoundRobinStrategyBuilder() );
//	lsbs.add(newMostCrowdedStrategyBuilder() );
//	Factory<LightSwitchingStrategy> lssFactory =newBuilderBasedFactory<>(lsbs);