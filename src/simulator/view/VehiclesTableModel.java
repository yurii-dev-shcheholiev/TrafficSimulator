package simulator.view;

import simulator.control.Controller;
import simulator.model.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

    private Controller _ctrl;
    private List<Vehicle> vTable;
    private String[] colNames;

    public VehiclesTableModel(Controller ctrl){
        super();
        _ctrl = ctrl;
        vTable = new ArrayList<>();
        _ctrl.addObserver(this);
        colNames = new String[]{"ID", "Status", "Itinerary", "CO2 Class", "Max. Speed", "Current Speed", "Total " +
                "CO2 Emitted", "Total Distance Travelled"};
    }

    @Override
    public int getRowCount() {
        return vTable.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    public String getColumnName(int i) { return colNames[i]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vehicle e = vTable.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return e.getId();
            case 1:
                switch (e.getStatus()){
                    case PENDING:
                        return "Pending";
                    case TRAVELING:
                        return e.getRoad() + ":" + e.getLocation();
                    case WAITING:
                       return "Waiting:j" + e.currentJunction();
                    case ARRIVED:
                        return "Arrived";
                }
            case 2:
                return e.getItinerary();
            case 3:
                return e.getContClass();
            case 4:
                return e.getMaxSpeed();
            case 5:
                return e.getSpeed();
            case 6:
                return e.getTotalContamination();
            case 7:
                return e.getTotalDistance();
        }

        return null;
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        table(map);
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        table(map);
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        table(map);
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        table(map);
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        table(map);
    }

    @Override
    public void onError(String err) {

    }


    private void table(RoadMap map){
        vTable = map.getVehicles();
        this.fireTableDataChanged();
    }

}
