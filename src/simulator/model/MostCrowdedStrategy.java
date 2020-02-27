package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {

	private int _timeSlot;

	public MostCrowdedStrategy(int timeSlot){
		_timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime, int currTime) {

		if (roads.isEmpty()) {
			return -1;
		}
		//Lights of all incoming roads
		if(currGreen == -1) {
			return circularSearch(qs, 0);
		}

		if ((currTime - lastSwitchingTime) < _timeSlot) {
			return currGreen;
		}
		//Giving green to incoming road with largest queue
		return circularSearch(qs, currGreen +1);
	}


	// method that does a circular search for both of them instead of doing a FOR.
	public int circularSearch(List<List<Vehicle>> qs, int position) {
		int m = position;
		int largest = m;

		do {
			m = m + 1 % qs.size();

			if (qs.get(m).size() > qs.get(largest).size()){
				largest = m;
			}
		} while (m != position);

		return largest;
	}
}//end
