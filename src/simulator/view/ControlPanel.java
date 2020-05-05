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
    private RoadMap _roadMap;
    private int _time;

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

        _ctrl.addObserver(this);
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
            if (_changeCO2Dialog.open(_roadMap.getVehicles()) == 1)
                _ctrl.addEvent(_changeCO2Dialog.getNewCO2Event());
        });
        add(_changeCO2Button);

        //Change Weather
        _changeWeatherButton = new JButton(new ImageIcon(iconsPath + "weather.png"));
        _changeWeatherButton.setToolTipText("Change Weather of a road");
        _changeWeatherButton.addActionListener(e -> {
            _changeWeatherDialog = new ChangeWeatherDialog();
            if (_changeWeatherDialog.open(_roadMap.getRoads()) == 1)
                _ctrl.addEvent(_changeWeatherDialog.getNewWeatherEvent());
        });
        add(_changeWeatherButton);

        //Run
        _runButton = new JButton(new ImageIcon(iconsPath + "run.png"));
        _runButton.setToolTipText("Run the simulation");
        _runButton.addActionListener(e -> {
            //TODO
        });
        add(_runButton);

        //Stop
        _stopButton = new JButton(new ImageIcon(iconsPath + "stop.png"));
        _stopButton.setToolTipText("Stop the simulation");
        _stopButton.addActionListener(e -> {
            //TODO
        });
        add(_stopButton);

        //Ticks
        //TODO

        //Exit
        _exitButton = new JButton(new ImageIcon(iconsPath + "exit.png"));
        _exitButton.setToolTipText("Exit the simulator");
        _exitButton.addActionListener(e -> {
            quit();
        });
        add(_exitButton);

    }

    private void quit() {
        int n = JOptionPane.showOptionDialog(this,
                "Are sure you want to quit?","Quit",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null,null, null);
        if (n == 0) {
             System.exit(0);
        }
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
        _roadMap = map;
        _time = time;
    }

    @Override
    public void onError(String err) {

    }
}
