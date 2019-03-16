package uk.co.zac_h.utils;

import com.leapmotion.leap.Vector;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class MovementThread implements Runnable {

    private final LeapController leapController;

    public MovementThread(LeapController leapController) {
        this.leapController = leapController;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        try {
            ArrayList<Vector> coordinates = loadCoordinates();
            MouseController mouseController = new MouseController();

            if (coordinates != null) while (true) {
                leapController.setCurrentFrame();

                if (!leapController.currentFrame.hands().isEmpty()) {

                    Vector position = leapController.getTouchTouchPosition();

                    if (position.magnitude() != 0) {

                        float xScaleFactor = (position.getX() - coordinates.get(0).getX()) / (coordinates.get(1).getX() - coordinates.get(0).getX());
                        float yScaleFactor = 1 - ((position.getY() - coordinates.get(2).getY()) / (coordinates.get(0).getY() - coordinates.get(2).getY()));

                        mouseController.setMousePosition(xScaleFactor, yScaleFactor);

                        if (position.getZ() < (coordinates.get(0).getZ() + coordinates.get(1).getZ() + coordinates.get(2).getZ()) / 3) {
                            mouseController.mousePress(InputEvent.BUTTON1_MASK);
                            mouseController.mouseRelease(InputEvent.BUTTON1_MASK);
                        }
                    }
                }
            }
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get saved calibration coordinates from properties file
     *
     * @return Calibration coordinates array
     * @throws IOException
     */
    private ArrayList<Vector> loadCoordinates() throws IOException {
        Properties properties = new Properties();

        properties.load(new FileInputStream("coordinates.properties"));

        ArrayList<Vector> arrayList = new ArrayList<>();
        arrayList.add(addCoordinates("left", properties));
        arrayList.add(addCoordinates("right", properties));
        arrayList.add(addCoordinates("bottom", properties));

        return arrayList;
    }

    private Vector addCoordinates(String position, Properties properties) {
        Vector vector = new Vector();

        vector.setX(Float.parseFloat(properties.getProperty(position + "_x")));
        vector.setY(Float.parseFloat(properties.getProperty(position + "_y")));
        vector.setZ(Float.parseFloat(properties.getProperty(position + "_z")));

        return vector;
    }
}
