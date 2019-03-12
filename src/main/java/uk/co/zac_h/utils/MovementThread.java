package uk.co.zac_h.utils;

import com.leapmotion.leap.Vector;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class MovementThread implements Runnable {

    private LeapController leapController;

    public MovementThread(LeapController leapController) {
        this.leapController = leapController;
    }

    @Override
    public void run() {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        ArrayList coordinates = loadCoordinates();
        MouseController mouseController = null;

        try {
            mouseController = new MouseController();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        if (coordinates != null && mouseController != null) {
            while (true) {
                leapController.setCurrentFrame();

                if (!leapController.currentFrame.hands().isEmpty()) {

                    Vector position = leapController.getTouchTouchPosition();

                    if (position.magnitude() != 0) {

                        float xScaleFactor = (position.getX() - ((Vector) coordinates.get(0)).getX()) / (((Vector) coordinates.get(1)).getX() - ((Vector) coordinates.get(0)).getX());
                        float yScaleFactor = 1 - ((position.getY() - ((Vector) coordinates.get(2)).getY()) / (((Vector) coordinates.get(0)).getY() - ((Vector) coordinates.get(2)).getY()));

                        int mouseX = (int) (xScaleFactor * graphicsDevice.getDisplayMode().getWidth());
                        int mouseY = (int) (yScaleFactor * graphicsDevice.getDisplayMode().getHeight());

                        mouseController.updateMousePosition(mouseX, mouseY);

                        if (position.getZ() < (((Vector) coordinates.get(0)).getZ() + ((Vector) coordinates.get(1)).getZ() + ((Vector) coordinates.get(2)).getZ()) / 3) {
                            mouseController.mousePress(InputEvent.BUTTON1_MASK);
                            mouseController.mouseRelease(InputEvent.BUTTON1_MASK);
                        }
                    }

                }
            }
        }
    }

    private ArrayList loadCoordinates() {
        Properties properties = new Properties();

        try (InputStream inputStream = new FileInputStream("coordinates.properties")) {
            properties.load(inputStream);

            Vector vectorLeft = new Vector();

            vectorLeft.setX(Float.parseFloat(properties.getProperty("left_x")));
            vectorLeft.setY(Float.parseFloat(properties.getProperty("left_y")));
            vectorLeft.setZ(Float.parseFloat(properties.getProperty("left_z")));

            Vector vectorRight = new Vector();

            vectorRight.setX(Float.parseFloat(properties.getProperty("right_x")));
            vectorRight.setY(Float.parseFloat(properties.getProperty("right_y")));
            vectorRight.setZ(Float.parseFloat(properties.getProperty("right_z")));

            Vector vectorBottom = new Vector();

            vectorBottom.setX(Float.parseFloat(properties.getProperty("bottom_x")));
            vectorBottom.setY(Float.parseFloat(properties.getProperty("bottom_y")));
            vectorBottom.setZ(Float.parseFloat(properties.getProperty("bottom_z")));

            ArrayList arrayList = new ArrayList();
            arrayList.add(vectorLeft);
            arrayList.add(vectorRight);
            arrayList.add(vectorBottom);

            return arrayList;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
