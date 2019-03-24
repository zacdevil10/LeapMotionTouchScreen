package uk.co.zac_h.ui.controller;

import com.leapmotion.leap.Vector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    @FXML public ImageView image_left_point;
    @FXML public ImageView image_right_point;
    @FXML public ImageView image_bottom_point;
    @FXML public Button end_calibration_button;

    private int position = 0;

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
            switch (position) {
                case 0:
                    setCurrentPointer(image_left_point, "images/calibration_target.png", false);
                    setCurrentPointer(image_right_point, "images/calibration_target_selected.png", true);

                    topLeftPoint = leapController.getTouchPointablePosition();
                    position++;
                    break;
                case 1:
                    setCurrentPointer(image_right_point, "images/calibration_target.png", false);
                    setCurrentPointer(image_bottom_point, "images/calibration_target_lower_selected.png", true);

                    topRightPoint = leapController.getTouchPointablePosition();
                    position++;
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

                    try (OutputStream outputStream = new FileOutputStream("coordinates.properties")){

                        properties.store(outputStream, null);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    position = 0;
                    break;
            }
        }
    }

    private void setCurrentPointer(ImageView image, String res, Boolean current) {
        image.setOpacity(current ? 1.0 : 0.5);
        image.setImage(new Image(res));
    }

    public void setLeapController(LeapController leapController) {
        this.leapController = leapController;
    }
}
