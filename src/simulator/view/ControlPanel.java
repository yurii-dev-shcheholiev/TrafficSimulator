package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.io.*;
import java.util.List;

public class ControlPanel extends JPanel implements TrafficSimObserver {

    private Controller _ctrl;

    private JButton loadEventFileButton;
    private JFileChooser eventFileChooser;
    private JButton changeContaminationClassButtom;
    private JButton changeWeatherButton;
    private JButton runButton;
    private JButton stopButton;
    private JButton ticksButton;
    private JButton exitButton;

    ControlPanel(Controller ctrl) {
        super();
        _ctrl = ctrl;

        initGUI();
    }

    private void initGUI() {
        //Load Events File
        eventFileChooser = new JFileChooser("Load Event File");
        loadEventFileButton = new JButton("Load Events");
        loadEventFileButton.addActionListener(e -> {
            try {
                int returnVal = eventFileChooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = eventFileChooser.getSelectedFile();
                    InputStream inputStream = new FileInputStream(file);

                    _ctrl.reset();
                    _ctrl.loadEvents(inputStream);
                }
            } catch (FileNotFoundException | NullPointerException ex) {
                JOptionPane.showMessageDialog(this.getParent(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(loadEventFileButton);




    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {

    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onError(String err) {

    }
}
