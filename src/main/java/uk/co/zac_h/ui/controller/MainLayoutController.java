package uk.co.zac_h.ui.controller;


import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Listener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import uk.co.zac_h.ui.CalibrationOverlay;
import uk.co.zac_h.utils.LeapController;
import uk.co.zac_h.utils.MovementThread;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController extends Listener implements Initializable, CalibrationLayoutController.Update {

    @FXML public ImageView closeButton;
    @FXML public Label leapStatusText;
    @FXML public Button buttonStartServiceMain;
    @FXML public Slider mouseSensSlider;
    @FXML private RadioButton touchClickModeRadio;
    @FXML private RadioButton twoFingerClickModeRadio;
    @FXML private RadioButton touchMouseModeRadio;
    @FXML private RadioButton mouseModeRadio;
    @FXML private RadioButton trayMinimizeRadio;
    @FXML private RadioButton taskBarMinimizeRadio;

    private MovementThread movementThread;
    private Thread movement;

    private Boolean isLeapRunning = false;

    private LeapController leapController;



    public MainLayoutController() {
        // Required empty public constructor

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup clickModeGroup = new ToggleGroup();
        ToggleGroup mouseModeGroup = new ToggleGroup();
        ToggleGroup minimizeGroup = new ToggleGroup();

        touchClickModeRadio.setToggleGroup(clickModeGroup);
        twoFingerClickModeRadio.setToggleGroup(clickModeGroup);

        touchMouseModeRadio.setToggleGroup(mouseModeGroup);
        mouseModeRadio.setToggleGroup(mouseModeGroup);

        trayMinimizeRadio.setToggleGroup(minimizeGroup);
        taskBarMinimizeRadio.setToggleGroup(minimizeGroup);

        closeButton.setOnMouseClicked(event -> System.exit(0));
    }

    public void setLeapController(LeapController leapController) {
        this.leapController = leapController;
        leapController.setControllerListener(this);
    }

    @FXML
    private void entered(MouseEvent event) {
        ((ImageView) event.getTarget()).setOpacity(1.0);
    }

    @FXML
    private void exited(MouseEvent event) {
        ((ImageView) event.getTarget()).setOpacity(0.7);
    }

    @FXML
    public void startMouseService() {
        if (!isLeapRunning) {
            movementThread = new MovementThread(leapController);

            movement = new Thread(movementThread);

            movement.start();

            buttonStartServiceMain.setText("Stop");
            isLeapRunning = true;
        } else {
            movementThread.stop();
            movement.interrupt();

            buttonStartServiceMain.setText("Start");
            isLeapRunning = false;
        }
    }

    @FXML
    private void startCalibration() throws IOException {
        CalibrationOverlay calibrationOverlay = new CalibrationOverlay();
        calibrationOverlay.start(leapController, this);
    }

    @Override
    public void calibrationUpdate() {
        if (isLeapRunning) movementThread.updateCoordinates();
    }

    @Override
    public void onConnect(Controller controller) {
        Platform.runLater(() -> leapStatusText.setText("Leap Motion status: Connected"));
    }

    @Override
    public void onDisconnect(Controller controller) {
        Platform.runLater(() -> leapStatusText.setText("Leap Motion status: Disconnected"));
    }
}
