package simulator.view;

import javafx.geometry.VerticalDirection;
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
        _event = "null";

        initGUI();

        _ctrl.addObserver(this);
    }

    private void initGUI() {

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        _timeLabel = new JLabel("Time:  " + _currTime);
//        _timeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        //_timeLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        this.add(_timeLabel);

        this.add(Box.createRigidArea(new Dimension(50,0)));

//        this.add(new JSeparator(SwingConstants.VERTICAL));

       //this.add(new JSeparator());

        _eventLabel = new JLabel("Event Added (" + _event + ")");
//        _eventLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //_eventLabel.setHorizontalAlignment(SwingConstants.HORIZONTAL);
        this.add(_eventLabel);

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));



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
