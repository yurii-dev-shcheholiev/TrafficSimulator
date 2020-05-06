package simulator.view;

import simulator.control.Controller;
import simulator.model.*;
import simulator.model.Event;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

    private static final int _JRADIUS = 10;
    private static final int _ISIZE = 32;
    private static final Color _BG_COLOR = Color.WHITE;
    private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
    private static Image _car;
    private RoadMap _map;

    MapByRoadComponent(Controller ctrl) {
        initGUI();
        ctrl.addObserver(this);
    }

    private void initGUI() {
        _car = loadImage("car.png");
        setPreferredSize(new Dimension(300,200));
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // clear with a background color
        g.setColor(_BG_COLOR);
        g.clearRect(0, 0, getWidth(), getHeight());

        if (_map == null || _map.getRoads().size() == 0) {
            g.setColor(Color.red);
            g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
        } else {

            drawMap(g);
        }
    }

    private void drawMap(Graphics g) {
        int x1= 50;
        int x2 = getWidth() - 100;
        int y = 0;
        int i = 0;

        for (Road r : _map.getRoads()) {
            //Roads
            y = (i + 1) * 50;

            g.setColor(Color.BLACK);
            g.drawString(r.getId(), x1 - 25, y + _JRADIUS / 4);

            g.drawLine(x1, y, x2, y);

            //Junctions
            //srcJunction
            g.setColor(Color.BLUE);
            g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

            g.setColor(_JUNCTION_LABEL_COLOR);
            g.drawString(r.getSrcJunction().getId(), x1 - _JRADIUS / 2, y - _JRADIUS);

            //destJunction
            //TODO how to get if junction has green or red ??
            if (r.getDestJunction().getGreenLightIndex() != -1)
                g.setColor(Color.GREEN);
            else
                g.setColor(Color.RED);
            g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

            g.setColor(_JUNCTION_LABEL_COLOR);
            g.drawString(r.getDestJunction().getId(), x2 - _JRADIUS / 2, y - _JRADIUS);

            //Vehicles
            for (Vehicle v : r.getVehicles()) {
                if (v.getStatus() != VehicleStatus.ARRIVED) {
                    int x = x1 + (int) ((x2 - x1) * ((double) v.getLocation() / (double) r.getLength()));

                    g.setColor(Color.GREEN);
                    g.fillOval(x - _JRADIUS / 2, y - _JRADIUS / 2, 14, 14);
                    g.drawImage(_car, x - _JRADIUS / 2, y - _JRADIUS / 2, 12, 12, this);

                    g.setColor(Color.BLACK);
                    g.drawString(v.getId(), x - _JRADIUS / 2, y - _JRADIUS / 2);
                }
            }

            Image weatherImg;
            switch (r.getWeather()) {
                case STORM:
                    weatherImg = loadImage("storm.png");
                    break;
                case SUNNY:
                    weatherImg = loadImage("sun.png");
                    break;
                case RAINY:
                    weatherImg = loadImage("rain.png");
                    break;
                case WINDY:
                    weatherImg = loadImage("wind.png");
                    break;
                case CLOUDY:
                    weatherImg = loadImage("cloud.png");
                    break;
                default:
                    weatherImg = null;
            }
            g.setColor(_BG_COLOR);
            g.drawImage(weatherImg, x2 + _JRADIUS / 2, y - 15, _ISIZE, _ISIZE, this);

            //TODO contamination level

            //Increment
            i++;
        }

    }

    private void drawContamination() {

    }

    private void drawWeather() {

    }

    private void drawRoads(Graphics g) {

    }

    private void drawJunctions(Graphics g) {

    }

    private void drawVehicles(Graphics g) {

    }

    private Image loadImage(String img) {
        Image i = null;
        try {
            return ImageIO.read(new File("resources/icons/" + img));
        } catch (IOException e) {
            System.out.println("Image load exception");
        }
        return i;
    }

    public void update(RoadMap roadMap) {
        _map = roadMap;
        repaint();
    }

    @Override
    public void onAdvanceStart(RoadMap map, List<Event> events, int time) {

    }

    @Override
    public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
        update(map);
    }

    @Override
    public void onReset(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onRegister(RoadMap map, List<Event> events, int time) {
        update(map);
    }

    @Override
    public void onError(String err) {

    }
}
