package simulator.model;
import simulator.misc.Pair;
import java.util.List;

public class SetContaminationClassEvent extends Event {

    private List<Pair<String, Integer>> _cs;

    public SetContaminationClassEvent(int time, List<Pair<String, Integer>> cs) {
        super(time);

        if (cs == null)
            throw new IllegalArgumentException("Contamination Value is Null");
        _cs = cs;
    }

    @Override
    void execute(RoadMap map) {

        for (Pair <String, Integer> c : _cs) {
            if(map.getVehicle(c.getFirst()) == null)
                throw new IllegalArgumentException("Vehicle doesn't exist in the RoadMap");
            else
                map.getVehicle(c.getFirst()).setContClass(c.getSecond());
        }//foreach

    }
}
