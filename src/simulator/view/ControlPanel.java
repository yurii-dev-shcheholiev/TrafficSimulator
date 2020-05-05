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
    private boolean _stopped;

    private JButton _loadEventFileButton;
    private JFileChooser _eventFileChooser;
    private JButton _changeCO2Button;
    private ChangeCO2ClassDialog _changeCO2Dialog;
    private JButton _changeWeatherButton;
    private ChangeWeatherDialog _changeWeatherDialog;
    private JButton _runButton;
    private JButton _stopButton;
    private JSpinner _ticksSpinner;
    private JButton _exitButton;

    ControlPanel(Controller ctrl) {
        super();
        _ctrl = ctrl;
        _stopped = false;
        _time = 0;
        initGUI();

        _ctrl.addObserver(this);
    }

    private void initGUI() {
        //TODO add lines or additional panels to group buttons

        // Create a relative path to resources/icons
        String absolutePath = new File("").getAbsolutePath();
        String iconsPath = absolutePath + "/resources/icons/";
        String fileLoaderPath = absolutePath + "/resources/examples/";

        //Load Events File
        _eventFileChooser = new JFileChooser("Load Event File");
        _eventFileChooser.setCurrentDirectory(new File(fileLoaderPath));

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
            run();
        });
        add(_runButton);

        //Stop
        _stopButton = new JButton(new ImageIcon(iconsPath + "stop.png"));
        _stopButton.setToolTipText("Stop the simulation");
        _stopButton.addActionListener(e -> {
            stop();
        });
        add(_stopButton);

        //Ticks
        JLabel ticksLabel = new JLabel("Ticks:");
        _ticksSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
        _ticksSpinner.setToolTipText("Simulation tick to run: 1-10000");
        _ticksSpinner.setMaximumSize(new Dimension(80, 40));
        _ticksSpinner.setMinimumSize(new Dimension(80, 40));
        _ticksSpinner.setPreferredSize(new Dimension(80, 40));
        add(ticksLabel);
        add(_ticksSpinner);

        //Exit
        _exitButton = new JButton(new ImageIcon(iconsPath + "exit.png"));
        _exitButton.setToolTipText("Exit the simulator");
        _exitButton.addActionListener(e -> {
            quit();
        });
        add(_exitButton);

    }

    private void run() {
        _stopped = false;
        enableToolBar(false);
        runSim((Integer) _ticksSpinner.getValue());
    }

    private void runSim(int n) {
        if (n > 0 && !_stopped){
            try {
                //TODO change 2 argument !!!
                _ctrl.run(1, System.out);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this.getParent(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                _stopped = true;
                return;
            }
            SwingUtilities.invokeLater(() -> runSim(n - 1));
        } else {
            enableToolBar(true);
            _stopped = true;
        }
    }

    private void enableToolBar(boolean val) {
        _loadEventFileButton.setEnabled(val);
        _changeCO2Button.setEnabled(val);
        _changeWeatherButton.setEnabled(val);
        _runButton.setEnabled(val);
    }

    private void stop() {
        _stopped = true;
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
