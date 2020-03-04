package simulator.model;
import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event {

    private String _id;
    private List<String> _itinerary;
    private int _maxSpeed;
    private int _contamination;

    public NewVehicleEvent (int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
        super(time);

        _id = id;
        _maxSpeed = maxSpeed;
        _contamination = contClass;
        _itinerary = itinerary;
    }

    @Override
    void execute(RoadMap map) {
        List<Junction> tmp = new ArrayList<>();
        for (String id: _itinerary) {
            tmp.add(map.getJunction(id));
        }
        map.addVehicle(new Vehicle(_id, _maxSpeed, _contamination, tmp));
    }
}
