package simulator.control;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Controller {
    private TrafficSimulator _trafSim;
    private Factory<Event> _evFactory;

    public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
        if (sim == null)
            throw new IllegalArgumentException("TrafficSimulator must not equal null");
        if (eventsFactory == null)
            throw new IllegalArgumentException("Events Factory must not equal null");
        _trafSim = sim;
        _evFactory = eventsFactory;
    }

    public void loadEvents(InputStream in) {
        JSONObject jo = new JSONObject(new JSONTokener(in));
        JSONArray ja = jo.getJSONArray("events");
        for (int i = 0; i < ja.length(); i++) {
            Event event = _evFactory.createInstance(ja.getJSONObject(i));
            if (event == null)
                throw new NullPointerException("JSON object does not match");
            _trafSim.addEvent(event);
        }
    }

    public void run(int n, OutputStream out) {
        PrintStream p = new PrintStream(out);
        JSONObject jo = new JSONObject();
        JSONArray ja = new JSONArray();
        for (int i = 0; i < n; i++) {
            _trafSim.advance();
            ja.put(_trafSim.report());
        }
        jo.put("states", ja);
        p.println(jo.toString(5));
    }

    public void reset() {
        _trafSim.reset();
    }
}
