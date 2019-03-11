package uk.co.zac_h.utils;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;

public class LeapController {

    private Controller controller;
    private Frame currentFrame;
    private Frame previousFrame;

    public LeapController() {
        controller = new Controller();
    }

    public void setCurrentFrame() {
        previousFrame = currentFrame;
        currentFrame = controller.frame();
    }

    public Vector getTouchFingerPosition() {
        return controller.frame().pointables().frontmost().tipPosition();
    }

    public boolean isConnected() {
        return controller.isConnected();
    }

    public void setControllerListener(Listener listener) {
        controller.addListener(listener);
    }
}
