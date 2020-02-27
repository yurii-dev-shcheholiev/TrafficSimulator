package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {

	public MoveAllStrategy() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {

			List<Vehicle> y = new ArrayList<Vehicle>(q);
	
		return y;
	}

}
