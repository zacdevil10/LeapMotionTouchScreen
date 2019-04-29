package uk.co.zac_h.utils;

import java.awt.*;
import java.awt.geom.Point2D;

class MouseController {

    private Robot robot;

    MouseController() throws AWTException {
        robot = new Robot();
    }

    /**
     * This method moves the mouse position to x, y
     */
    private void updateMousePosition(int x, int y) {
        robot.mouseMove(x, y);
    }


    /**
     * This method scrolls the mouse in a given direction
     *
     * @param wheelAmt is the direction and amount to scroll
     */
    public void mouseScroll(int wheelAmt) {
        robot.mouseWheel(wheelAmt);
    }

    /**
     * This method clicks a mouse button
     *
     * @param button is the button to press
     */
    void mouseClick(int button) {
        robot.mousePress(button);
        robot.mouseRelease(button);
    }

    /**
     * This method presses a mouse button
     *
     * @param button is the button to press
     */
    void mouseHold(int button) {
        robot.mousePress(button);
    }

    /**
     * This method releases a mouse button
     *
     * @param button is the button to release
     */
    void mouseRelease(int button) {
        robot.mouseRelease(button);
    }

    /**
     * This method calculates the new position of the mouse
     */
    void setMousePosition(Point2D.Float coordinates) {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        int mouseX = (int) (coordinates.getX() * graphicsDevice.getDisplayMode().getWidth());
        int mouseY = (int) (coordinates.getY() * graphicsDevice.getDisplayMode().getHeight());

        updateMousePosition(mouseX, mouseY);
    }
}
