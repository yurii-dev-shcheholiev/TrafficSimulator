package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	
	private int _timeSlot;
	
	public MostCrowdedStrategy(int timeSlot) {
		_timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<Vehicle> qs, int currGreen, int lastSwitchingTime, int currTime) {
		
		if (roads.isEmpty()) return -1;
		
		//TODO:Lights of all incoming roads
		
		if ((currTime - lastSwitchingTime) < _timeSlot) return currGreen;
		
		//TODO:Giving green to incoming road with largest queue
		
		
		return 0;
	}

}
