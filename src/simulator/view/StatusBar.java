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
    private JLabel _timeLabel;
    private JLabel _eventLabel;

    StatusBar(Controller ctrl) {
        super();
        _ctrl = ctrl;
        initGUI();
        _ctrl.addObserver(this);
    }

    private void initGUI() {

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        _timeLabel = new JLabel(currentTime(0));

        this.add(_timeLabel);

        this.add(Box.createRigidArea(new Dimension(50,0)));

//        this.add(new JSeparator(SwingConstants.VERTICAL));

       //this.add(new JSeparator());

        _eventLabel = new JLabel(noEvent());
        this.add(_eventLabel);

        this.setBorder(BorderFactory.createLineBorder(Color.GRAY));

    }
    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

        _timeLabel.setText(currentTime(time));

    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {

        _timeLabel.setText(currentTime(time));

    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

        _timeLabel.setText(currentTime(time));
        _eventLabel.setText(getEvent(e.toString()));
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {

        _timeLabel.setText(currentTime(time));
        _eventLabel.setText(noEvent());
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {

        _timeLabel.setText(currentTime(time));
        //_eventLabel.setText(noEvent());
    }

    @Override
    public void onError(String err) {

    }


    private String currentTime(int time){
        return "Time:  " + time;
    }


    private String getEvent(String e){
        return "Event Added (" + e + ")";
    }

    private String noEvent(){
        return "Go Speed Racer! Go!";
    }

    private String blank(){
        return "";
    }



}
