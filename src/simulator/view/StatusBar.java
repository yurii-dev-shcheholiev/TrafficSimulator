package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatusBar extends JPanel implements TrafficSimObserver {

    private Controller _ctrl;
    private int _currTime;
    private String _event;
    private JLabel _timeLabel;
    //private JPanel _Status;
    private JLabel _eventLabel;

    StatusBar(Controller ctrl) {
        super();
        _ctrl = ctrl;
        _currTime = 0;
        _event = null;

        initGUI();

        _ctrl.addObserver(this);
    }

    private void initGUI() {

        _timeLabel = new JLabel(String.valueOf(_currTime));
        _timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        _eventLabel = new JLabel("Event Added " + _event);
        _eventLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(_timeLabel);
        this.add( _eventLabel);

    }
    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
        _currTime = time;
    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        _currTime = time;
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

        _currTime = time;
        _event = e.toString();

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {

        _currTime = time;
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {

        _currTime = time;

    }

    @Override
    public void onError(String err) {

    }
}
