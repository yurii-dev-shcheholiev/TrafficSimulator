package simulator.view;

import extra.dialog.Dish;
import simulator.model.Vehicle;

import javax.swing.*;

public class ChangeCO2ClassDialog extends JDialog {

    private int _status;

    private JComboBox<Vehicle> _vehicles;
    private DefaultComboBoxModel<Dish> _vehicleModel;
    private JComboBox<Integer> _co2classes;
    private DefaultComboBoxModel<Integer> _co2Model;
    private JComboBox<Integer> _tics;
    private DefaultComboBoxModel<Integer> _ticModel;


    ChangeCO2ClassDialog() {
        super();
        initGUI();
    }

    private void initGUI() {
        _status = 0;

        setTitle("Change CO2 Class");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setContentPane(mainPanel);


    }
}
