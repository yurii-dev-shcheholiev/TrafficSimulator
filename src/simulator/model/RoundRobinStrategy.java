package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy {
	
	private int _timeSlot;
	
	public RoundRobinStrategy(int timeSlot) {
		
		_timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<Vehicle> qs, int currGreen, int lastSwitchingTime, int currTime) {
		
		if (roads.isEmpty()) return -1;
		
		if(currGreen == -1) return 0;
		
		if ((currTime - lastSwitchingTime) < _timeSlot) return currGreen;
		else return ((currGreen + 1) % roads.size()); //TODO: It should be revised. I believe that there's more to add. To consult with teacher
	}

}
