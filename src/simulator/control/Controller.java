package simulator.control;

import org.json.JSONObject;
import org.json.JSONTokener;
import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

import java.io.InputStream;
import java.io.OutputStream;

public class Controller {
    private TrafficSimulator _trafSim;
    private Factory<Event> _evFactory;

    public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
        _trafSim = sim;
        _evFactory = eventsFactory;
    }

    public void loadEvents(InputStream in) {
        JSONObject jo = new JSONObject(new JSONTokener(in));
    }

    public void run(int n, OutputStream out) {

    }

    public void reset() {
        _trafSim = new TrafficSimulator();
    }
}
