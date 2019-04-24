package uk.co.zac_h.ui.controller;

import com.leapmotion.leap.Vector;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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

    public interface Update {
        void calibrationUpdate();
    }

    private Update update;

    @FXML
    private AnchorPane container;
    @FXML
    private ImageView imageLeftPoint;
    @FXML
    private ImageView imageRightPoint;
    @FXML
    private ImageView imageBottomPoint;

    //Calibration images
    private static final String CAL_TARGET = "images/calibration_target.png";
    private static final String CAL_TARGET_SELECTED = "images/calibration_target_selected.png";
    private static final String CAL_TARGET_LOWER = "images/calibration_target_lower.png";
    private static final String CAL_TARGET_LOWER_SELECTED = "images/calibration_target_lower_selected.png";

    private int position = 0;

    private Vector topLeftPoint;
    private Vector topRightPoint;
    private Vector bottomCenterPoint;

    private LeapController leapController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Required
    }

    public void calibrationConfirm(KeyEvent keyEvent) {
        Properties properties = new Properties();

        switch (keyEvent.getCode()) {
            case ENTER:
                switch (position) {
                    case 0:
                        setCurrentPointer(imageLeftPoint, CAL_TARGET, false);
                        setCurrentPointer(imageRightPoint, CAL_TARGET_SELECTED, true);

                        topLeftPoint = leapController.getTouchPointablePosition();
                        position++;
                        break;
                    case 1:
                        setCurrentPointer(imageRightPoint, CAL_TARGET, false);
                        setCurrentPointer(imageBottomPoint, CAL_TARGET_LOWER_SELECTED, true);

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

                        close();

                        break;
                    default:
                        break;
                }
                break;
            case R:
                position = 0;

                setCurrentPointer(imageLeftPoint, CAL_TARGET_SELECTED, true);
                setCurrentPointer(imageRightPoint, CAL_TARGET, false);
                setCurrentPointer(imageBottomPoint, CAL_TARGET_LOWER, false);

                break;
            case ESCAPE:
                close();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + keyEvent.getCode());
        }
    }

    private void setCurrentPointer(ImageView image, String res, Boolean current) {
        image.setOpacity(current ? 1.0 : 0.5);
        image.setImage(new Image(res));
    }

    public void setUpdateInterface(Update update) {
        this.update = update;
    }

    public void setLeapController(LeapController leapController) {
        this.leapController = leapController;
    }

    @FXML
    private void close() {
        update.calibrationUpdate();
        ((Stage) container.getScene().getWindow()).close();
    }
}
