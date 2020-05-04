package simulator.view;

import simulator.model.Road;
import simulator.model.Vehicle;
import simulator.model.Weather;

import javax.swing.*;
import java.awt.*;

public class ChangeWeatherDialog extends JDialog {

    private int _status;

    private JLabel _descLabel;
    private JComboBox<Road> _roads;
    private DefaultComboBoxModel<Road> _roadModel;
    private JComboBox<Weather> _weather;
    private DefaultComboBoxModel<Weather> _weatherModel;
    private JComboBox<Integer> _ticks;
    private DefaultComboBoxModel<Integer> _tickModel;

    ChangeWeatherDialog() {
        super();
        initGUI();
    }

    private void initGUI() {
        _status = 0;

        setTitle("Change Road Weather");

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
        _descLabel = new JLabel("<html>Schedule an event to change the weather of a road after a <br>given number of simulation ticks from now.</html>");
        _descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(_descLabel, BorderLayout.PAGE_START);

        //Vehicle comboBox
        JLabel rLabel = new JLabel("Road:");
        _roadModel = new DefaultComboBoxModel<>();
        _roads = new JComboBox<Road>(_roadModel);
        boxesPanel.add(rLabel);
        boxesPanel.add(_roads);

        //CO2 comboBox
        JLabel weatherLabel = new JLabel("Weather:");
        _weatherModel = new DefaultComboBoxModel<>();
        _weather = new JComboBox<Weather>(_weatherModel);
        boxesPanel.add(weatherLabel);
        boxesPanel.add(_weather);

        //Tics comboBox
        JLabel ticksLabel = new JLabel("Ticks:");
        _tickModel = new DefaultComboBoxModel<>();
        _ticks = new JComboBox<Integer>(_tickModel);
        boxesPanel.add(ticksLabel);
        boxesPanel.add(_ticks);

        //Cancel Button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            _status = 0;
            ChangeWeatherDialog.this.setVisible(false);
        });
        buttonsPanel.add(cancelButton);

        //Ok button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            if (_roadModel.getSelectedItem() != null
                    && _weatherModel.getSelectedItem() != null
                    && _tickModel.getSelectedItem() != null) {
                _status = 1;
                ChangeWeatherDialog.this.setVisible(false);
            }
        });
        buttonsPanel.add(okButton);

        //Dialog setups
        setPreferredSize(new Dimension(500, 300));
        pack();
        setResizable(false);
        setModal(true);
        setVisible(true);
    }

    //TODO create methods to get Lists for ComboBoxes + create methods to return the new selected values
}
