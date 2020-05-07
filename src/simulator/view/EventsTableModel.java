package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.table.AbstractTableModel;
import java.util.List;

//TODO Need to Fix that there are All ones at the begining and that remain in the table

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

    private Controller _ctrl;
    private Object[][] eTable;
    private String[] colNames;

    public EventsTableModel(Controller ctrl){
        super();
        _ctrl = ctrl;
        eTable = new Object[50][50];
        _ctrl.addObserver(this);
        colNames = new String[]{"Time", "Description"};
    }

    @Override
    public int getRowCount() {
        return eTable[0].length;
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    public String getColumnName(int i) { return colNames[i]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return eTable[rowIndex][columnIndex];
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        table(events);
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        table(events);
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        table(events);
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        table(events);
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        table(events);
    }

    @Override
    public void onError(String err) {

    }


    private void table(List<Event> events){

        clearTable();
        for (int i = 0; i < events.size(); i++){

            eTable[i][0] = events.get(i).getTime();
            eTable[i][1] = events.get(i).toString();
        }

        this.fireTableDataChanged();
    }

    private void clearTable(){


    }


}
