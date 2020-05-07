package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

//TODO Need to Fix that there are All ones at the begining and that remain in the table

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

    private Controller _ctrl;
    private List<Event> eTable;
    private String[] colNames;

    public EventsTableModel(Controller ctrl){
        super();
        _ctrl = ctrl;
        eTable = new ArrayList<>();
        colNames = new String[]{"Time", "Description"};
        _ctrl.addObserver(this);
    }

    @Override
    public int getRowCount() {
        return eTable.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    public String getColumnName(int i) { return colNames[i]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Event e = eTable.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return e.getTime();
            case 1:
                return e.toString();
        }
        return null;
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
        eTable = events;
        this.fireTableDataChanged();
    }


}
