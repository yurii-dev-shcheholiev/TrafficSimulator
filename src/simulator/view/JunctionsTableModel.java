package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

    private Controller _ctrl;
    private Object[][] jTable;
    public JunctionsTableModel(Controller ctrl){
        super();
        _ctrl = ctrl;
        jTable = new Object[50][50];
        _ctrl.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return jTable[0].length;
    }

    @Override
    public int getColumnCount() {
        return jTable.length;
    }

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
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) { table(map); }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) { table(map); }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) { table(map); }

    @Override
    public void onError(String err) {

    }


    private void table(RoadMap map){

        for (int i = 0; i < map.getJunctions().size(); i++){

                jTable[i][0] = map.getJunctions().get(i);
                jTable[i][1] = map.getJunctions().get(i).getId();

                //TODO Better implementation of Incoming Roads with Green Light as in the PDF

                jTable[i][2] = map.getJunctions().get(i).getInRoads();

                 //TODO Better Implementation of Queues as in the PDF

                jTable[i][3] = map.getJunctions().get(i).getQueues();
        }
    }

}
