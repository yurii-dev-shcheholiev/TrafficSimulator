package simulator.view;

import simulator.misc.Pair;
import simulator.model.SetContaminationClassEvent;
import simulator.model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChangeCO2ClassDialog extends JDialog {

    private int _status;
    private int _time;

    private JLabel _descLabel;
    private JComboBox<String> _vehicles;
    private DefaultComboBoxModel<String> _vehicleModel;
    private JComboBox<Integer> _co2classes;
    private DefaultComboBoxModel<Integer> _co2Model;
    private JSpinner _ticks;


    ChangeCO2ClassDialog() {
        super();
        initGUI();
    }

    private void initGUI() {
        _status = 0;

        setTitle("Change CO2 Class");
        //Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setContentPane(mainPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        //Top Panel(Label)
        JPanel topPanel = new JPanel();
        topPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(topPanel);

        //ComboBoxes Panel
        JPanel boxesPanel = new JPanel();
        boxesPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(boxesPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        //Buttons Panel
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
        mainPanel.add(buttonsPanel);

        //Label
        _descLabel = new JLabel("<html>Schedule an event to change the CO2 class of a vehicle after a <br>given number of simulation ticks from now.</html>");
        _descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(_descLabel, BorderLayout.PAGE_START);

        //Vehicle comboBox
        JLabel vLabel = new JLabel("Vehicle:");
        _vehicleModel = new DefaultComboBoxModel<>();
        _vehicles = new JComboBox<String>(_vehicleModel);
        boxesPanel.add(vLabel);
        boxesPanel.add(_vehicles);

        //CO2 comboBox
        JLabel co2Label = new JLabel("CO2 Class:");
        _co2Model = new DefaultComboBoxModel<>();
        _co2classes = new JComboBox<Integer>(_co2Model);
        boxesPanel.add(co2Label);
        boxesPanel.add(_co2classes);

        //Tics Spinner
        JLabel ticksLabel = new JLabel("Ticks:");
        _ticks = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
        _ticks.setToolTipText("Simulation tick to run: 1-10000");
//        _ticks.setMaximumSize(new Dimension(80, 40));
//        _ticks.setMinimumSize(new Dimension(80, 40));
//        _ticks.setPreferredSize(new Dimension(80, 40));
        boxesPanel.add(ticksLabel);
        boxesPanel.add(_ticks);

        //Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            _status = 0;
            ChangeCO2ClassDialog.this.setVisible(false);
        });
        buttonsPanel.add(cancelButton);

        //Ok button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            if (_vehicleModel.getSelectedItem() != null
                    && _co2Model.getSelectedItem() != null) {
                _status = 1;
                ChangeCO2ClassDialog.this.setVisible(false);
            }
        });
        buttonsPanel.add(okButton);

        //Dialog setups
        setPreferredSize(new Dimension(500, 300));
        pack();
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(null);
        setVisible(false);
    }

    public int open(int time, List<Vehicle> vehicles) {
        _time = time;
        _vehicleModel.removeAllElements();
        for (Vehicle v : vehicles) {
            _vehicleModel.addElement(v.getId());
        }
        for (int i = 0; i < 10; i++) {
            _co2Model.addElement(i);
        }

        setVisible(true);
        return _status;
    }

    public SetContaminationClassEvent getNewCO2Event() {
        List<Pair<String, Integer>> tmp = new ArrayList<Pair<String, Integer>>();
        tmp.add(new Pair<String, Integer>((String) _vehicleModel.getSelectedItem(), (Integer) _co2Model.getSelectedItem()));
        return new SetContaminationClassEvent(_time + (Integer)_ticks.getValue(), tmp);
    }
}
