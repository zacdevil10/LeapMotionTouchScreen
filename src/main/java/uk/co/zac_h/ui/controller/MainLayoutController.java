package uk.co.zac_h.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import uk.co.zac_h.ui.CalibrationOverlay;

import java.net.URL;
import java.util.ResourceBundle;

public class MainLayoutController implements Initializable {

    @FXML public ImageView close_button;
    @FXML private RadioButton touch_click_mode_radio;
    @FXML private RadioButton two_finger_click_mode_radio;

    @FXML private RadioButton touch_mouse_mode_radio;
    @FXML private RadioButton mouse_mode_radio;

    @FXML private RadioButton tray_minimize_radio;
    @FXML private RadioButton task_bar_minimize_radio;

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

    @FXML
    private void entered(MouseEvent event) {
        ((ImageView) event.getTarget()).setOpacity(1.0);
    }

    @FXML
    private void exited(MouseEvent event) {
        ((ImageView) event.getTarget()).setOpacity(0.7);
    }

    @FXML
    private void startCalibration() throws Exception {
        CalibrationOverlay calibrationOverlay = new CalibrationOverlay();
        calibrationOverlay.start();
    }

}
