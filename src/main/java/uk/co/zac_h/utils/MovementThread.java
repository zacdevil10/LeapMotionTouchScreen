package uk.co.zac_h.utils;

import com.leapmotion.leap.Vector;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovementThread implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(MovementThread.class.getName());

    private final LeapController leapController;

    private ArrayList<Vector> coordinates = new ArrayList<>();

    private Boolean run = true;
    public Boolean mouseMode = false;

    public MovementThread(LeapController leapController) {
        this.leapController = leapController;
    }

    @Override
    public void run() {
        try {
            coordinates = loadCoordinates();
            MouseController mouseController = new MouseController();

            if (!coordinates.isEmpty()) while (run) {
                leapController.setCurrentFrame();

                if (!leapController.currentFrame.hands().isEmpty()) {

                    Vector position = leapController.getTouchTouchPosition();

                    if (position.magnitude() != 0) {

                        if (mouseMode) {
                            mouseController.setMousePosition(mousePosition(position));

                            if (position.getZ() < 0) {
                                mouseController.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
                            }

                        } else {
                            mouseController.setMousePosition(scale(position));
                            setTouchPlane(coordinates, mouseController, position);
                        }
                    }
                }
            }
        } catch (AWTException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }
    }

    private void setTouchPlane(ArrayList<Vector> coordinates, MouseController mouseController, Vector position) {
        if (position.getZ() < (coordinates.get(0).getZ() + coordinates.get(1).getZ() + coordinates.get(2).getZ()) / 3) {
            mouseController.mouseClick(InputEvent.BUTTON1_DOWN_MASK);
        }
    }

    public void stop() {
        run = false;
    }

    /**
     * Get saved calibration coordinates from properties file
     *
     * @return Calibration coordinates array
     */
    private ArrayList<Vector> loadCoordinates() {
        ArrayList<Vector> arrayList = new ArrayList<>();

        try (InputStream inputStream = new FileInputStream("coordinates.properties")) {
            Properties properties = new Properties();

            properties.load(inputStream);

            arrayList.add(addCoordinates("left", properties));
            arrayList.add(addCoordinates("right", properties));
            arrayList.add(addCoordinates("bottom", properties));

            return arrayList;
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage());
        }

        return arrayList;
    }

    public void updateCoordinates() {
        coordinates = loadCoordinates();
    }

    private Vector addCoordinates(String position, Properties properties) {
        Vector vector = new Vector();

        vector.setX(Float.parseFloat(properties.getProperty(position + "_x")));
        vector.setY(Float.parseFloat(properties.getProperty(position + "_y")));
        vector.setZ(Float.parseFloat(properties.getProperty(position + "_z")));

        return vector;
    }

    private Point2D.Float scale(Vector position) {
        float xScaleFactor = (position.getX() - coordinates.get(0).getX()) / (coordinates.get(1).getX() - coordinates.get(0).getX());
        float yScaleFactor = (1 - ((position.getY() - coordinates.get(2).getY()) / (coordinates.get(0).getY() - coordinates.get(2).getY())));

        return new Point2D.Float(xScaleFactor, yScaleFactor);
    }

    private Point2D.Float mousePosition(Vector position) {
        float x = (float) ((position.getX() + (position.getX() / 2)) * 0.01);
        float y = (float) (1 - ((position.getY()) * 0.01));

        return new Point2D.Float(x, y + 2);
    }
}
