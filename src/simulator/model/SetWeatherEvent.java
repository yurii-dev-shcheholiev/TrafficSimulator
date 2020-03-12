package simulator.model;
import simulator.misc.Pair;
import java.util.List;

public class SetWeatherEvent extends Event {

    private List<Pair<String, Weather>> _ws;

    public SetWeatherEvent(int time, List<Pair<String, Weather>> ws) {
        super(time);

        if (ws == null)
            throw new IllegalArgumentException("Weather Value is Null");
        _ws = ws;
    }

    @Override
    void execute(RoadMap map) {
        for (Pair <String, Weather> w : _ws) {
            if (map.getRoad(w.getFirst()) == null)
                throw new IllegalArgumentException("Road doesn't exist in the RoadMap");
            else
                map.getRoad(w.getFirst()).setWeather(w.getSecond());
        }//foreach
    }
}
