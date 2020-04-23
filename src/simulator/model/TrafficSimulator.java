package simulator.model;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

import java.util.List;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
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
        _events.clear();
        _roadMap.reset();
    }

    public JSONObject report() {
        JSONObject ob = new JSONObject();
        ob.put("time", _time);
        ob.put("state", _roadMap.report());
        return ob;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }
}
