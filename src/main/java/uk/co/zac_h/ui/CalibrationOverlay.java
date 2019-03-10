package uk.co.zac_h.ui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Objects;

public class CalibrationOverlay {

    public void start() throws Exception {
        Stage secondaryStage = new Stage();
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("layout_calibration.fxml")));
        Scene scene = new Scene(root, Color.TRANSPARENT);

        secondaryStage.setScene(scene);
        secondaryStage.setTitle("Leap Motion Touch Screen");
        secondaryStage.setHeight(graphicsDevice.getDisplayMode().getHeight());
        secondaryStage.setWidth(graphicsDevice.getDisplayMode().getWidth());
        secondaryStage.setFullScreen(true);
        secondaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        secondaryStage.show();
    }
}
