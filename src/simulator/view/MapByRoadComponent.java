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
            //calculate y of all components
            y = (i + 1) * 50;

            drawRoads(g, r, x1, x2, y);

            drawJunctions(g, r, x1, x2, y);

            drawVehicles(g, r, x1, x2, y);

            drawWeather(g, r, x1, x2, y);

            drawContamination(g, r, x1, x2, y);

            //Increment
            i++;
        }
    }

    private void drawRoads(Graphics g, Road r, int x1, int x2, int y) {
        g.setColor(Color.BLACK);
        g.drawString(r.getId(), x1 - 30, y + _JRADIUS / 4);
        g.drawLine(x1, y, x2, y);
    }

    private void drawJunctions(Graphics g, Road r, int x1, int x2, int y) {
        //srcJunction
        g.setColor(Color.BLUE);
        g.fillOval(x1 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

        g.setColor(_JUNCTION_LABEL_COLOR);
        g.drawString(r.getSrcJunction().getId(), x1 - _JRADIUS / 2, y - _JRADIUS);

        //destJunction
        if (r.getDestJunction().getGreenLightIndex() != -1
                && r.equals(r.getDestJunction().getInRoads().get(r.getDestJunction().getGreenLightIndex())) )
            g.setColor(Color.GREEN);
        else
            g.setColor(Color.RED);
        g.fillOval(x2 - _JRADIUS / 2, y - _JRADIUS / 2, _JRADIUS, _JRADIUS);

        g.setColor(_JUNCTION_LABEL_COLOR);
        g.drawString(r.getDestJunction().getId(), x2 - _JRADIUS / 2, y - _JRADIUS);
    }

    private void drawVehicles(Graphics g, Road r, int x1, int x2, int y) {
        for (Vehicle v : r.getVehicles()) {
            if (v.getStatus() != VehicleStatus.ARRIVED) {
                int x = x1 + (int) ((x2 - x1) * ((double) v.getLocation() / (double) r.getLength()));

                g.setColor(Color.GREEN);
                g.drawImage(_car, x - _JRADIUS / 2 , y - _JRADIUS, 16, 16, this);

                // Choose a color for the vehcile's label and background, depending on its
                // contamination class
                int vLabelColor = (int) (25.0 * (10.0 - (double) v.getContClass()));
                g.setColor(new Color(0, vLabelColor, 0));
                g.drawString(v.getId(), x - 4, y - _JRADIUS);
            }
        }
    }

    private void drawContamination(Graphics g, Road r, int x1, int x2, int y) {
        int c = (int) Math.floor(Math.min((double)r.getTotalContamination() / (1.0 + (double)r.getContLimit() ), 1.0) / 0.19);
        Image co2Img;
        switch (c) {
            case 0:
                co2Img = loadImage("cont_0.png");
                break;
            case 1:
                co2Img = loadImage("cont_1.png");
                break;
            case 2:
                co2Img = loadImage("cont_2.png");
                break;
            case 3:
                co2Img = loadImage("cont_3.png");
                break;
            case 4:
                co2Img = loadImage("cont_4.png");
                break;
            case 5:
                co2Img = loadImage("cont_5.png");
                break;
            default:
                co2Img = null;
        }
        g.setColor(_BG_COLOR);
        g.drawImage(co2Img, x2 + 50, y - 15, _ISIZE, _ISIZE, this);
    }

    private void drawWeather(Graphics g, Road r, int x1, int x2, int y) {
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
        g.drawImage(weatherImg, x2 + 11, y - 15, _ISIZE, _ISIZE, this);
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
