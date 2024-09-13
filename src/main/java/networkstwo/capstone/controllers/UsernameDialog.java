package networkstwo.capstone.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static networkstwo.capstone.utils.Validator.validateUsername;
import static networkstwo.capstone.utils.Screen.showAlert;

public class UsernameDialog {

    @FXML
    private TextField textField;

    private String username = "";

    @FXML
    void cancelPressed(MouseEvent event) {
        Stage stage = (Stage) textField.getScene().getWindow();
        stage.close();
    }

    @FXML
    void okPressed(MouseEvent event) {
        String username = textField.getText();
        if (validateUsername(username)){
            this.username = username;
            Stage stage = (Stage) textField.getScene().getWindow();
            stage.close();
        }else{
            showAlert(Alert.AlertType.ERROR, "Invalid username", "Please enter a valid username");
            textField.setText("");
        }
    }

    public String getUsername() {
        return username;
    }

}
