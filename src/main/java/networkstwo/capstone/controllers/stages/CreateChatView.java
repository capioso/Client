package networkstwo.capstone.controllers.stages;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import networkstwo.capstone.messages.CreateChat;
import networkstwo.capstone.models.Operation;
import networkstwo.capstone.models.User;
import networkstwo.capstone.services.MessageSender;

import java.util.UUID;

import static networkstwo.capstone.utils.FullValidationUtils.acceptUsername;
import static networkstwo.capstone.utils.ScreenUtils.showAlert;

public class CreateChatView {

    @FXML
    private Text textField;

    @FXML
    private TextField usernameText;

    private UUID data = null;

    @FXML
    public void initialize() throws Exception {
        textUpdater();
    }

    @FXML
    void cancelPressed(MouseEvent event) {
        Stage stage = (Stage) textField.getScene().getWindow();
        stage.close();
    }

    @FXML
    void okPressed(MouseEvent event) {
        try {
            String username = usernameText.getText();
            acceptUsername(username);

            UUID chatId = UUID.randomUUID();
            CreateChat getMessage = new CreateChat(User.getToken(), Operation.CREATE_CHAT.name(), chatId, username, "");
            JsonNode response = MessageSender.getResponse(getMessage);

            String responseTitle = response.get("title").asText();
            String body = response.get("body").asText();

            if (!responseTitle.equals("message")) {
                throw new Exception(body);
            }
            this.data = chatId;
            Stage stage = (Stage) textField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            textField.setText("");
        }
    }

    private void textUpdater() {
        ChangeListener<String> textFieldListener = (observable, oldValue, newValue) -> textField.setText(newValue);
        usernameText.textProperty().addListener(textFieldListener);
    }

    public UUID getData() {
        return data;
    }
}
