package simulator.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

public class RoadMap {
    private List<Junction> juncList;
    private List<Road> roadList;
    private List<Vehicle> vehicleList;
    private Map<String, Junction> juncMap;
    private Map<String, Road> roadMap;
    private Map<String, Vehicle> vehicleMap;

    RoadMap() {
        juncList = new LinkedList<>();
        roadList = new LinkedList<>();
        vehicleList = new LinkedList<>();
        juncMap = new HashMap<>();
        roadMap = new HashMap<>();
        vehicleMap = new HashMap<>();
    }

    void addJunction(Junction j) {
        if (juncMap.get(j.getId()) == j)
            throw new IllegalArgumentException("Junction is already in the RoadMap");
        juncList.add(j);
        juncMap.put(j.getId(), j);
    }

    void addRoad(Road r) {
        if (roadMap.get(r.getId()) == r)
            throw new IllegalArgumentException("Road is already in the RoadMap");
        if ( !(juncMap.containsValue(r.getSrcJunction()) && juncMap.containsValue(r.getDestJunction())) )
            throw new IllegalArgumentException("Junctions connected to this road are not in the RoadMap");
        roadList.add(r);
        roadMap.put(r.getId(), r);
    }

    void addVehicle(Vehicle v) {
        if (vehicleMap.get(v.getId()) == v)
            throw new IllegalArgumentException("Vehicle is already in the RoadMap");
        for (Junction j : v.getItinerary()) {
            if (!juncMap.containsValue(j))
                throw new IllegalArgumentException("Junctions of the itinerary are not in the RoadMap");
        }
        vehicleList.add(v);
        vehicleMap.put(v.getId(), v);
    }

    public Junction getJunction(String id) {
        return juncMap.get(id);
    }

    public Road getRoad(String id) {
        return roadMap.get(id);
    }

    public Vehicle getVehicle(String id) {
        return vehicleMap.get(id);
    }

    public List<Junction> getJunctions() {
        return Collections.unmodifiableList(new ArrayList<>(juncList));
    }

    public List<Road> getRoads() {
        return Collections.unmodifiableList(new ArrayList<>(roadList));
    }

    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(new ArrayList<>(vehicleList));
    }

    void reset() {
        juncList.clear();
        roadList.clear();
        vehicleList.clear();
        juncMap.clear();
        roadMap.clear();
        vehicleMap.clear();
    }

    public JSONObject report() {
        JSONObject ob = new JSONObject();

        JSONArray junc = new JSONArray();
        for (Junction j : juncList) {
            junc.put(j.report());
        }
        ob.put("junctions", junc);

        JSONArray roads = new JSONArray();
        for (Road r : roadList) {
            roads.put(r.report());
        }
        ob.put("roads", roads);

        JSONArray veh = new JSONArray();
        for (Vehicle v : vehicleList) {
            veh.put(v.report());
        }
        ob.put("vehicles", veh);

        return ob;
    }
}
