package uk.co.zac_h.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import uk.co.zac_h.ui.controller.CalibrationLayoutController;
import uk.co.zac_h.utils.LeapController;

import java.awt.*;
import java.io.IOException;

public class CalibrationOverlay {

    public void start(LeapController leapController) throws IOException {
        FXMLLoader calibration = new FXMLLoader(getClass().getClassLoader().getResource("layout_calibration.fxml"));

        Stage secondaryStage = new Stage();
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Parent root = calibration.load();
        Scene scene = new Scene(root, Color.TRANSPARENT);

        secondaryStage.setScene(scene);
        secondaryStage.setTitle("Leap Motion Touch Screen");
        secondaryStage.setHeight(graphicsDevice.getDisplayMode().getHeight());
        secondaryStage.setWidth(graphicsDevice.getDisplayMode().getWidth());
        secondaryStage.setFullScreen(true);
        secondaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        secondaryStage.show();

        CalibrationLayoutController calibrationLayoutController = calibration.getController();

        calibrationLayoutController.setLeapController(leapController);
    }
}
