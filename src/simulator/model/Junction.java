package simulator.model;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class Junction extends SimulatedObject{
	
	private List<Road> _inRoads;
	private Map<Junction, Road> _outRoads;
	private List<List<Vehicle>> _queues;
	private Map<Road, List<Vehicle>> _mapQ;
	private int _greenL;
	private int _lastSwitchT;
	private LightSwitchingStrategy _lsStrategy;
	private DequeuingStrategy _dqStrategy;
	private int _xCoor, _yCoor;
	
	

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor ) {
		super(id);
		
		if (lsStrategy.equals(null) && dqStrategy.equals(null)) 
			throw new IllegalArgumentException("The Light Switch and the Dequeing must not be empty");
		
		if(xCoor < 0 && yCoor < 0) 
			throw new IllegalArgumentException("The x and y coordinates must be either 0 or of positive value");
		
		
		_lsStrategy = lsStrategy;
		_dqStrategy = dqStrategy;
		_xCoor = xCoor;
		_yCoor = yCoor;
		
		
		// TODO initialize the rest
	}

	
	void addInCommingRoad(Road r) {
		//TODO
		
	}
	
	
	void addOutGoingRoad(Road r) {
		
		//TODO
	}
	
	
	void enter(Road r, Vehicle v) {
		
		//TODO
	}
	
	
	Road roadTo(Junction j) {
		
		//TODO
		
		return null;
	}
	
	
	@Override
	void advance(int time) {
		// TODO 
		
	}

	@Override
	public JSONObject report() {
		// TODO 
		return null;
	}
}
