package uk.co.zac_h;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import uk.co.zac_h.ui.controller.MainLayoutController;
import uk.co.zac_h.utils.LeapController;
import uk.co.zac_h.utils.MovementThread;

public class LeapMotionTouchScreen extends Application {

    private Double xOffset = 0.0;
    private Double yOffset = 0.0;

    private Thread movement;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader main = new FXMLLoader(getClass().getClassLoader().getResource("layout_main.fxml"));

        Parent root = main.load();
        Scene scene = new Scene(root, Color.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Leap Motion Touch Screen");
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

        root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        MainLayoutController mainLayoutController = main.getController();

        LeapController leapController = new LeapController();

        mainLayoutController.setLeapController(leapController);

        MovementThread movementThread = new MovementThread(leapController);

        movement = new Thread(movementThread);

        movement.start();
    }


}
