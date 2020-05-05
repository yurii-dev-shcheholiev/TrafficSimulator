package simulator.view;

import simulator.misc.Pair;
import simulator.model.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ChangeWeatherDialog extends JDialog {

    private int _status;

    private JLabel _descLabel;
    private JComboBox<String> _roads;
    private DefaultComboBoxModel<String> _roadModel;
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
        _roads = new JComboBox<String>(_roadModel);
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
        setVisible(false);
    }

    //TODO find out why passed argument List<Road> roads is empty
    public int open(List<Road> roads) {
        System.out.println(roads.size());
        _roadModel.removeAllElements();
        for (Road r : roads) {
            _roadModel.addElement(r.getId());
        }
        System.out.println(_roadModel.getSize());
        for (Weather w : Weather.values()) {
            _weatherModel.addElement(w);
        }

        for (int i = 0; i < 50; i++) {
            _tickModel.addElement(i);
        }
        // launch the window
        setVisible(true);
        return _status;
    }

    public SetWeatherEvent getNewWeatherEvent() {
        List<Pair<String, Weather>> tmp = new ArrayList<Pair<String, Weather>>();
        tmp.add(new Pair<String, Weather>((String) _roadModel.getSelectedItem(), (Weather) _weatherModel.getSelectedItem()));
        return new SetWeatherEvent((Integer) _tickModel.getSelectedItem(), tmp);
    }
}
