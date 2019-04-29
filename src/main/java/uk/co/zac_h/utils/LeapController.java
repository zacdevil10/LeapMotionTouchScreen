package uk.co.zac_h.utils;

import com.leapmotion.leap.*;

public class LeapController {

    private final Controller controller;
    Frame currentFrame;
    private Frame previousFrame;

    public LeapController() {
        controller = new Controller();
        controller.setPolicy(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
    }

    void setCurrentFrame() {
        previousFrame = currentFrame;
        currentFrame = controller.frame();
    }

    public Vector getTouchPointablePosition() {
        return controller.frame().pointables().frontmost().tipPosition();
    }

    Vector getTouchTouchPosition() {
        return currentFrame.hands().rightmost().fingers().fingerType(Finger.Type.TYPE_INDEX).extended().frontmost().stabilizedTipPosition();
    }

    public void setControllerListener(Listener listener) {
        controller.addListener(listener);
    }
}
