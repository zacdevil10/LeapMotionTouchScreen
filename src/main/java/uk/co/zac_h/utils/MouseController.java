package uk.co.zac_h.utils;

import java.awt.*;

public class MouseController {

    private Robot robot;

    public MouseController() throws AWTException {
        robot = new Robot();
    }

    /**
     * This method moves the mouse position to x, y
     */
    public void updateMousePosition(int x, int y) {
        robot.mouseMove(x, y);
    }


    /**
     * This method scrolls the mouse in a given direction
     *
     * @param wheelAmt is the direction and amount to scroll
     * */
    public void mouseScroll(int wheelAmt) {
        robot.mouseWheel(wheelAmt);
    }

    /**
     * This method presses a mouse button
     *
     * @param button is the button to press
     */
    public void mousePress(int button) {
        robot.mousePress(button);
    }

    /**
     * This method releases a mouse button
     *
     * @param button is the button to release
     */
    public void mouseRelease(int button) {
        robot.mouseRelease(button);
    }

    /**
     * This method calculates the new position of the mouse
     */
    public void setMousePosition() {
        updateMousePosition(0, 0);
    }
}
