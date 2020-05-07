package simulator.view;

import simulator.control.Controller;
import simulator.model.*;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

    private Controller _ctrl;
    private List<Junction> jTable;
    private String[] colNames;

    public JunctionsTableModel(Controller ctrl){
        super();
        _ctrl = ctrl;
        jTable = new ArrayList<>();
        _ctrl.addObserver(this);
        colNames = new String[]{"ID", "Green Light", "Queues"};
    }

    @Override
    public int getRowCount() {
        return jTable.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    public String getColumnName(int i) { return colNames[i]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Junction j = jTable.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return j.getId();
            case 1:
                if(j.getGreenLightIndex() != -1){
                    return j.getInRoads().get(j.getGreenLightIndex());
                } else return "NONE";
            case 2:
                String q = "";
                for (int i = 0; i < j.getInRoads().size(); i++) {
                    q = q + j.getInRoads().get(i) + ":" + j.getQueues().get(i) + " ";
                }
                return q;
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

        jTable = map.getJunctions();
        this.fireTableDataChanged();
    }


}
