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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;
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
        Properties properties = new Properties();
        if (keyEvent.getCode() == KeyCode.ENTER) {
            switch (count) {
                case 0:
                    topLeftPoint = leapController.getTouchPointablePosition();
                    count++;
                    break;
                case 1:
                    topRightPoint = leapController.getTouchPointablePosition();
                    count++;
                    break;
                case 2:
                    bottomCenterPoint = leapController.getTouchPointablePosition();
                    properties.setProperty("left_x", String.valueOf(topLeftPoint.getX()));
                    properties.setProperty("left_y", String.valueOf(topLeftPoint.getY()));
                    properties.setProperty("left_z", String.valueOf(topLeftPoint.getZ()));

                    properties.setProperty("right_x", String.valueOf(topRightPoint.getX()));
                    properties.setProperty("right_y", String.valueOf(topRightPoint.getY()));
                    properties.setProperty("right_z", String.valueOf(topRightPoint.getZ()));

                    properties.setProperty("bottom_x", String.valueOf(bottomCenterPoint.getX()));
                    properties.setProperty("bottom_y", String.valueOf(bottomCenterPoint.getY()));
                    properties.setProperty("bottom_z", String.valueOf(bottomCenterPoint.getZ()));

                    OutputStream outputStream = null;

                    try {
                        outputStream = new FileOutputStream("coordinates.properties");

                        properties.store(outputStream, null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    count = 0;
                    break;
            }
        }
    }

    public void setLeapController(LeapController leapController) {
        this.leapController = leapController;
    }
}
