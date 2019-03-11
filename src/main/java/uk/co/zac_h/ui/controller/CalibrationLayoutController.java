package uk.co.zac_h.ui.controller;

import com.leapmotion.leap.Vector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import uk.co.zac_h.utils.LeapController;

import java.net.URL;
import java.util.ResourceBundle;

public class CalibrationLayoutController implements Initializable {

    @FXML public AnchorPane container;
    @FXML private Button end_calibration_button;

    private int count = 0;

    private Vector topLeftPoint;
    private Vector topRightPoint;
    private Vector bottomCenterPoint;

    private LeapController leapController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void close() {
        ((Stage) end_calibration_button.getScene().getWindow()).close();
    }

    @FXML
    private void targetClicked(MouseEvent event) {
        System.out.println("X: " + event.getScreenX() + ", Y: " + event.getScreenY());
    }

    public void calibration_confirm(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            switch (count) {
                case 0:
                    topLeftPoint = leapController.getTouchFingerPosition();
                    count++;
                    break;
                case 1:
                    topRightPoint = leapController.getTouchFingerPosition();
                    count++;
                    break;
                case 2:
                    bottomCenterPoint = leapController.getTouchFingerPosition();
                    System.out.println("Left: " + topLeftPoint + "\nRight: " + topRightPoint + "\nBottom: " + bottomCenterPoint);
                    count = 0;
                    break;
            }
        }
    }

    public void setLeapController(LeapController leapController) {
        this.leapController = leapController;
    }
}
