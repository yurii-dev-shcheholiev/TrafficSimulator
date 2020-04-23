package simulator.model;

import javafx.beans.InvalidationListener;
import org.json.JSONObject;
import simulator.misc.SortedArrayList;

import java.util.ArrayList;
import java.util.List;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
    private List<TrafficSimObserver> _observerList;
    private RoadMap _roadMap;
    private List<Event> _events;
    private int _time;

    public TrafficSimulator() {
        _time = 0;
        _events = new SortedArrayList<>();
        _roadMap = new RoadMap();
        _observerList = new ArrayList<>();
    }

    public void addEvent(Event e) {
        _events.add(e);
        for (TrafficSimObserver ob : _observerList) {
            ob.onEventAdded(_roadMap, _events, e, _time);
        }
    }

    public void advance() {
        try {
            _time++;
            for (TrafficSimObserver ob : _observerList) {
                ob.onAdvanceStart(_roadMap, _events, _time);
            }
            while (_events.size() > 0 && _events.get(0).getTime() == _time) {
            _events.remove(0).execute(_roadMap);
            }
            for (Junction j : _roadMap.getJunctions()) {
                j.advance(_time);
            }
            for (Road r : _roadMap.getRoads()) {
                r.advance(_time);
            }
            for (TrafficSimObserver ob : _observerList) {
                ob.onAdvanceEnd(_roadMap, _events, _time);
            }
        } catch (Exception e) {
            for (TrafficSimObserver ob : _observerList) {
                ob.onError(e.getMessage());
            }
            throw e;
        }
    }

    public void reset() {
        _time = 0;
        _events.clear();
        _roadMap.reset();
        for (TrafficSimObserver ob : _observerList) {
            ob.onReset(_roadMap, _events, _time);
        }
    }

    public JSONObject report() {
        JSONObject ob = new JSONObject();
        ob.put("time", _time);
        ob.put("state", _roadMap.report());
        return ob;
    }

    @Override
    public void addObserver(TrafficSimObserver o) {
        _observerList.add(o);
        o.onRegister(_roadMap, _events, _time);
    }

    @Override
    public void removeObserver(TrafficSimObserver o) {
        _observerList.remove(o);
    }
}
