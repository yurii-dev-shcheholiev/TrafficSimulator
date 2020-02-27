package simulator.model;
import java.util.List;

public class NewVehicleEvent extends Event {

    private String _id;
    private List<Junction> _itinerary;
    private int _maxSpeed;
    private int _contamination;

    public NewVehicleEvent (int time, String id, int maxSpeed, int contClass, List<Junction> itinerary) {
        super(time);

        _id = id;
        _maxSpeed = maxSpeed;
        _contamination = contClass;
        _itinerary = itinerary;
    }

    @Override
    void execute(RoadMap map) {
        map.addVehicle(new Vehicle(_id, _maxSpeed, _contamination, _itinerary));
    }
}
