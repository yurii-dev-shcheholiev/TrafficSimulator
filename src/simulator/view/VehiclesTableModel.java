package simulator.view;

import simulator.control.Controller;
import simulator.model.*;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

    private Controller _ctrl;
    private Object[][] vTable;
    private String[] colNames;

    public VehiclesTableModel(Controller ctrl){
        super();
        _ctrl = ctrl;
        vTable = new Object[50][50];
        _ctrl.addObserver(this);
        colNames = new String[]{"ID", "Status", "Itinerary", "CO2 Class", "Max. Speed", "Current Speed", "Total " +
                "CO2 Emitted", "Total Distance Travelled"};
    }

    @Override
    public int getRowCount() {
        return vTable[0].length;
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    public String getColumnName(int i) { return colNames[i]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return vTable[rowIndex][columnIndex];
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

        for (int i = 0; i < map.getVehicles().size(); i++){

                vTable[i][0] = map.getVehicles().get(i).getId();


                switch (map.getVehicles().get(i).getStatus()){
                    case PENDING:
                        vTable[i][1] = "Pending";
                        break;
                    case TRAVELING:
                        vTable[i][1] = map.getVehicles().get(i).getRoad()+ ":" + map.getVehicles().get(i).getLocation();
                        break;
                    case WAITING:
                        vTable[i][1] = "Waiting:j" + map.getVehicles().get(i).currentJunction();
                        break;
                    case ARRIVED:
                        vTable[i][1] = "Arrived";
                        break;
                }

                vTable[i][2] = map.getVehicles().get(i).getItinerary();
                vTable[i][3] = map.getVehicles().get(i).getContClass();
                vTable[i][4] = map.getVehicles().get(i).getMaxSpeed();
                vTable[i][5] = map.getVehicles().get(i).getSpeed();
                vTable[i][6] = map.getVehicles().get(i).getTotalDistance();
        }

        this.fireTableDataChanged();

    }

}
