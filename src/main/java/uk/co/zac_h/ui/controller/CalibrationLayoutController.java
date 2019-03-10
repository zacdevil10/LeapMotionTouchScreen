package uk.co.zac_h.ui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CalibrationLayoutController implements Initializable {

    @FXML
    private Button end_calibration_button;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void close() {
        ((Stage) end_calibration_button.getScene().getWindow()).close();
    }

}
