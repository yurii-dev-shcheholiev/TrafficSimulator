package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

import java.util.List;

public class TrafficSimulator {
    private RoadMap _roadMap;
    private List<Event> _events;
    private int _time;

    public TrafficSimulator() {
        _time = 0;
        _events = new SortedArrayList<>();
        _roadMap = new RoadMap();
    }

    public void addEvent(Event e) {
        _events.add(e);
    }

    public void advance() {
        _time++;
        while (_events.size() > 0 && _events.get(0).getTime() == _time) {
            _events.remove(0).execute(_roadMap);
        }
        for (Junction j : _roadMap.getJunctions()) {
            j.advance(_time);
        }
        for (Road r : _roadMap.getRoads()) {
            r.advance(_time);
        }
    }

    public void reset() {
        _time = 0;
        _events = new SortedArrayList<>();
        _roadMap = new RoadMap();
    }

    public JSONObject report() {
        JSONObject ob = new JSONObject();
        ob.put("time", _time);

        JSONObject stateOb = new JSONObject();
        stateOb.put("junctions", new JSONArray(_roadMap.getJunctions()));
        stateOb.put("roads", new JSONArray(_roadMap.getRoads()));
        stateOb.put("vehicles", new JSONArray(_roadMap.getVehicles()));
        ob.put("state", stateOb);
        return ob;
    }
}
