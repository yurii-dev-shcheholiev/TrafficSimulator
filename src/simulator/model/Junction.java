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

		_greenL = 0;
		_lastSwitchT = 0;

		_lsStrategy = lsStrategy;
		_dqStrategy = dqStrategy;
		_xCoor = xCoor;
		_yCoor = yCoor;
		
		_inRoads = new ArrayList<Road>();
		_outRoads = new HashMap<Junction, Road>();
		_queues = new ArrayList<List<Vehicle>>();
		_mapQ = new HashMap<Road, List<Vehicle>>();
	}

	void addInCommingRoad(Road r) {
		if (!(r.getDestJunction().equals(this)))
			throw new IllegalArgumentException("Destination is not Equal to Current Junction");
		_inRoads.add(r);
		List<Vehicle> q = new LinkedList<>();
		_queues.add(q);
		_mapQ.put(r, q);
	}//addInCommingRoad
	
	
	void addOutGoingRoad(Road r) {
		Junction j = r.getDestJunction();
		if (_outRoads.containsKey(j) && !(j.equals(this)))
			throw new IllegalArgumentException("Other Roads go to Junction and is Not a Destination");
		_outRoads.put(j, r);
	}
	
	
	void enter(Road r, Vehicle v) {
		r.enter(v);
	}
	
	
	Road roadTo(Junction j) {
		return _outRoads.get(j);
	}


	@Override
	void advance(int time) {
		if (_greenL != -1){
			List<Vehicle> q =  _queues.get(_greenL);
			List<Vehicle> l = _dqStrategy.dequeue(q);

			for (Vehicle v: l) {
				v.moveToNextRoad();
				q.remove(v);
			}//foreach
		}//if

		//switch light
		int g = _lsStrategy.chooseNextGreen( _inRoads, _queues.get(_greenL) ,_greenL, _lastSwitchT, time);

		if ( _greenL != g ){
			_greenL = g;
			_lastSwitchT = time;
		}//if
	}


	@Override
	public JSONObject report() {

		JSONObject j = new JSONObject();
		j.put("id", _id);
		if (_greenL != -1) {
			j.put("green", _inRoads.get(_greenL)._id);
		} else {
			j.put("green", "none");
		}

		JSONObject qu = new JSONObject();
		for ( Road rx : _inRoads) {
			qu.put("roads", rx.getId());

			for (Vehicle vx : _mapQ.get(rx)) {
				qu.put("vehicles", vx.getId());
			}
		}
		j.put("queues", qu);

		return j;
	}
}//End
