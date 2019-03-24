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

import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController extends Listener implements Initializable {

    @FXML public ImageView close_button;
    @FXML public Label leap_status_text;
    @FXML public Button button_start_service_main;
    @FXML public Slider mouse_sens_slider;
    @FXML private RadioButton touch_click_mode_radio;
    @FXML private RadioButton two_finger_click_mode_radio;
    @FXML private RadioButton touch_mouse_mode_radio;
    @FXML private RadioButton mouse_mode_radio;
    @FXML private RadioButton tray_minimize_radio;
    @FXML private RadioButton task_bar_minimize_radio;

    private MovementThread movementThread;
    private Thread movement;

    private Boolean isLeapRunning = false;

    private LeapController leapController;

    public MainLayoutController() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup clickModeGroup = new ToggleGroup();
        ToggleGroup mouseModeGroup = new ToggleGroup();
        ToggleGroup minimizeGroup = new ToggleGroup();

        touch_click_mode_radio.setToggleGroup(clickModeGroup);
        two_finger_click_mode_radio.setToggleGroup(clickModeGroup);

        touch_mouse_mode_radio.setToggleGroup(mouseModeGroup);
        mouse_mode_radio.setToggleGroup(mouseModeGroup);

        tray_minimize_radio.setToggleGroup(minimizeGroup);
        task_bar_minimize_radio.setToggleGroup(minimizeGroup);

        close_button.setOnMouseClicked(event -> System.exit(0));
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

            button_start_service_main.setText("Stop");
            isLeapRunning = true;
        } else {
            movementThread.stop();
            movement.interrupt();

            button_start_service_main.setText("Start");
            isLeapRunning = false;
        }
    }

    @FXML
    private void startCalibration() throws Exception {
        CalibrationOverlay calibrationOverlay = new CalibrationOverlay();
        calibrationOverlay.start(leapController);
    }

    @Override
    public void onConnect(Controller controller) {
        Platform.runLater(() -> leap_status_text.setText("Leap Motion status: Connected"));
    }

    @Override
    public void onDisconnect(Controller controller) {
        Platform.runLater(() -> leap_status_text.setText("Leap Motion status: Disconnected"));
    }
}
