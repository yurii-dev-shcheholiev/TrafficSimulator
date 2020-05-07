package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

    private Controller _ctrl;
    private Object[][] jTable;
    private String[] colNames;

    public JunctionsTableModel(Controller ctrl){
        super();
        _ctrl = ctrl;
        jTable = new Object[50][50];
        _ctrl.addObserver(this);
        colNames = new String[]{"ID", "Green Light", "Queues"};
    }

    @Override
    public int getRowCount() {
        return jTable[0].length;
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    public String getColumnName(int i) { return colNames[i]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return jTable[rowIndex][columnIndex];
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

        for (int i = 0; i < map.getJunctions().size(); i++){

            jTable[i][0] = map.getJunctions().get(i).getId();

            if(map.getJunctions().get(i).getGreenLightIndex() != -1){
                jTable[i][1] = map.getJunctions().get(i).getInRoads().get(map.getJunctions().get(i).getGreenLightIndex());
            } else jTable[i][1] = "NONE";

            //TODO Still Apply Better Implementation of Queues as in the PDF
            for (Road r : map.getJunctions().get(i).getInRoads()) {
                jTable[i][2] = r.getId() + ":" + map.getJunctions().get(i).getQueues();
            }
        }

        this.fireTableDataChanged();
    }


}
