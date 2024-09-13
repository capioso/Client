package networkstwo.capstone.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import networkstwo.capstone.messages.GetUser;
import networkstwo.capstone.models.Operation;
import networkstwo.capstone.services.ResponseServer;

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
        try {
            String username = textField.getText();
            if (!validateUsername(username)) {
                throw new Exception("Bad username");
            }
            GetUser getMessage = new GetUser();
            getMessage.setOperation(Operation.GET_USER.name());
            getMessage.setUsername(username);
            String response = ResponseServer.getResponse(getMessage);
            if (!response.equals("User with username " + username + " exists")) {
                throw new Exception(response);
            }
            this.username = username;
            Stage stage = (Stage) textField.getScene().getWindow();
            stage.close();
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            textField.setText("");
        }
    }

    public String getUsername() {
        return username;
    }

}
