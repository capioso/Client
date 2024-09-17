package networkstwo.capstone.controllers.views;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import networkstwo.capstone.messages.CreateChat;
import networkstwo.capstone.models.Operation;
import networkstwo.capstone.models.User;
import networkstwo.capstone.services.MessageSender;

import static networkstwo.capstone.utils.ValidationUtils.validateUsername;
import static networkstwo.capstone.utils.ScreenUtils.showAlert;

public class UsernameView {

    @FXML
    private TextField textField;

    @FXML
    private TextField usernameText;

    private String data;

    @FXML
    void cancelPressed(MouseEvent event) {
        Stage stage = (Stage) textField.getScene().getWindow();
        stage.close();
    }

    @FXML
    void okPressed(MouseEvent event) {
        try {
            String username = usernameText.getText();
            String title = textField.getText();
            if (!validateUsername(username) || !validateUsername(title)) {
                throw new Exception("Bad username or title");
            }
            CreateChat getMessage = new CreateChat(Operation.CREATE_CHAT.name(), title, username, User.getToken());
            JsonNode response = MessageSender.getResponse(getMessage);
            String responseTitle = response.get("title").asText();
            String body = response.get("body").asText();
            if (!responseTitle.equals("message")) {
                throw new Exception(body);
            }
            this.data = title;
            Stage stage = (Stage) textField.getScene().getWindow();
            stage.close();
        }catch (Exception e){
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            textField.setText("");
        }
    }

    public String getData() {
        return data;
    }

}
