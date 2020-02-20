package simulator.model;

import java.util.*;

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
		
		_inRoads = new ArrayList<Road>();
		_outRoads = new HashMap<Junction, Road>();
		_queues = new ArrayList<List<Vehicle>>();
		_mapQ = new HashMap<Road, List<Vehicle>>();
		
		// TODO initialize the rest
	}

	
	void addInCommingRoad(Road r) {
		//TODO
		
		_inRoads.add(r);
		
		List<Vehicle> q = new LinkedList<>();
		_queues.add(q);

		_mapQ.put(r, q);

		if (!(r.getDestJunction().equals(this)))
			throw new IllegalArgumentException("Destination is not Equal to Current Junction");

	}
	
	
	void addOutGoingRoad(Road r) {
		//TODO
		
		//if(_outRoads.containsValue(r))
		Junction j = r.getDestJunction();

		if (_outRoads.containsKey(j) && !(j.equals(this)))
			throw new IllegalArgumentException("Other Roads go to Junction and is Not a Destination");

		_outRoads.put(j, r);

	}
	
	
	void enter(Road r, Vehicle v) {
		//TODO
		r.enter(v);
	}
	
	
	Road roadTo(Junction j) {
		return _outRoads.get(j);
	}


	@Override
	void advance(int time) {
		// TODO


		
	}


	@Override
	public JSONObject report() {
		// TODO

		JSONObject

		return null;
	}


}
