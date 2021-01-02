package uk.co.zac_h.utils;

import com.leapmotion.leap.*;

import java.awt.geom.Point2D;
import java.util.List;

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

    Point2D.Float scale(Vector position, List<Vector> coordinates) {
        float xScaleFactor = (position.getX() - coordinates.get(0).getX()) / (coordinates.get(1).getX() - coordinates.get(0).getX());
        float yScaleFactor = (1 - ((position.getY() - coordinates.get(2).getY()) / (coordinates.get(0).getY() - coordinates.get(2).getY())));

        return new Point2D.Float(xScaleFactor, yScaleFactor);
    }
}
