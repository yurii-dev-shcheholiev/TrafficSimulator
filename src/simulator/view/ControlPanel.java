package simulator.view;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

public class ControlPanel extends JPanel implements TrafficSimObserver {

    private Controller _ctrl;

    private JButton _loadEventFileButton;
    private JFileChooser _eventFileChooser;
    private JButton _changeCO2Button;
    private ChangeCO2ClassDialog _changeCO2Dialog;
    private JButton _changeWeatherButton;
    private ChangeWeatherDialog _changeWeatherDialog;
    private JButton _runButton;
    private JButton _stopButton;
    private JButton _ticksButton;
    private JButton _exitButton;

    ControlPanel(Controller ctrl) {
        super();
        _ctrl = ctrl;

        initGUI();
    }

    private void initGUI() {
        // Create a relative path to resources/icons
        String iconsPath = new File("").getAbsolutePath() + "/resources/icons/";


        //Load Events File
        _eventFileChooser = new JFileChooser("Load Event File");

        _loadEventFileButton = new JButton(new ImageIcon(iconsPath + "open.png"));
        _loadEventFileButton.setToolTipText("Load Events file");
        _loadEventFileButton.addActionListener(e -> {
            try {
                int returnVal = _eventFileChooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = _eventFileChooser.getSelectedFile();
                    InputStream inputStream = new FileInputStream(file);

                    _ctrl.reset();
                    _ctrl.loadEvents(inputStream);
                }
            } catch (FileNotFoundException | NullPointerException ex) {
                JOptionPane.showMessageDialog(this.getParent(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(_loadEventFileButton);

        //Change CO2 Class
        _changeCO2Button = new JButton(new ImageIcon(iconsPath + "co2class.png"));
        _changeCO2Button.setToolTipText("Change CO2 class of a vehicle");
        _changeCO2Button.addActionListener(e -> {
            _changeCO2Dialog = new ChangeCO2ClassDialog();

            _ctrl.addEvent(null);
        });
        add(_changeCO2Button);

        //Change Weather
        _changeWeatherButton = new JButton(new ImageIcon(iconsPath + "weather.png"));
        _changeWeatherButton.setToolTipText("Change Weather of a road");
        _changeWeatherButton.addActionListener(e -> {
            _changeWeatherDialog = new ChangeWeatherDialog();
        });
        add(_changeWeatherButton);

        //Run
        _runButton = new JButton(new ImageIcon(iconsPath + "run.png"));
        _runButton.setToolTipText("Run the simulation");
        _runButton.addActionListener(e -> {

        });
        add(_runButton);

        //Stop
        _stopButton = new JButton(new ImageIcon(iconsPath + "stop.png"));
        _stopButton.setToolTipText("Stop the simulation");
        _stopButton.addActionListener(e -> {

        });
        add(_stopButton);

        //Ticks


        //Exit
        _exitButton = new JButton(new ImageIcon(iconsPath + "exit.png"));
        _exitButton.setToolTipText("Exit the simulator");
        _exitButton.addActionListener(e -> {
            System.exit(0);
        });
        add(_exitButton);

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
