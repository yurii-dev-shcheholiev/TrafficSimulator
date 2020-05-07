package simulator.view;

import simulator.control.Controller;
import simulator.model.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

    private Controller _ctrl;
    private List<Road> rTable;
    private String[] colNames;

    public RoadsTableModel(Controller ctrl){
        super();
        _ctrl = ctrl;
        rTable = new ArrayList<>();
        _ctrl.addObserver(this);
        colNames = new String[]{"ID", "Length", "Max. Speed", "Current Speed Limit", "Total CO2 Emitted",
                "CO2 Limit"};
    }

    @Override
    public int getRowCount() {
        return rTable.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    public String getColumnName(int i) { return colNames[i]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        Road r = rTable.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return r.getId();
            case 1:
                return r.getLength();
            case 2:
                return r.getWeather();
            case 3:
                return r.getMaxSpeed();
            case 4:
                return r.getSpeedLimit();
            case 5:
                return r.getTotalContamination();
            case 6:
                return r.getContLimit();
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
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time){ table(map); }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time){ table(map); }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time){ table(map); }

    @Override
    public void onError(String err) {

    }


    private void table(RoadMap map){
        rTable = map.getRoads();
        this.fireTableDataChanged();
    }

}
