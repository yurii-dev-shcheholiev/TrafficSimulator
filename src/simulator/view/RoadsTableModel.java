package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

    private Controller _ctrl;
    private Object[][] rTable;
    public RoadsTableModel(Controller ctrl){
        super();
        _ctrl = ctrl;
        rTable = new Object[50][50];
        _ctrl.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return rTable[0].length;
    }

    @Override
    public int getColumnCount() {
        return rTable.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rTable[rowIndex][columnIndex];
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

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) { table(map); }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onError(String err) {

    }


    private void table(RoadMap map){

        for (int i = 0; i < map.getRoads().size(); i++){

                rTable[i][0] = map.getRoads().get(i);
                rTable[i][1] = map.getRoads().get(i).getId();
                rTable[i][2] = map.getRoads().get(i).getLength();
                rTable[i][3] = map.getRoads().get(i).getWeather();
                rTable[i][4] = map.getRoads().get(i).getMaxSpeed();
                rTable[i][5] = map.getRoads().get(i).getSpeedLimit();
                rTable[i][6] = map.getRoads().get(i).getTotalContamination();
                rTable[i][7] = map.getRoads().get(i).getContLimit();
        }
    }

}
