package networkstwo.capstone.controllers.stages;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import networkstwo.capstone.messages.CreateChat;
import networkstwo.capstone.models.Chat;
import networkstwo.capstone.models.Event;
import networkstwo.capstone.models.Operation;
import networkstwo.capstone.models.User;
import networkstwo.capstone.services.EventBus;
import networkstwo.capstone.services.MessageSender;

import java.util.UUID;

import static networkstwo.capstone.services.UserServices.updateTitleById;
import static networkstwo.capstone.utils.FullValidationUtils.acceptTitle;
import static networkstwo.capstone.utils.FullValidationUtils.acceptUsername;

public class CreateGroupView {
    @FXML
    private TextField titleText;

    @FXML
    private TextField usernameText;

    private UUID chatId;

    @FXML
    void cancelPressed(MouseEvent event) {
        Stage stage = (Stage) titleText.getScene().getWindow();
        stage.close();
    }

    @FXML
    void okPressed(MouseEvent event) {
        try {
            String username = usernameText.getText();
            String title = titleText.getText();
            acceptUsername(username);
            acceptTitle(title);

            CreateChat getMessage = new CreateChat(User.getToken(), Operation.CREATE_CHAT.name(), chatId, username, title);
            JsonNode response = MessageSender.getResponse(getMessage);

            System.out.println(response);

            updateTitleById(chatId, title);
            EventBus.getInstance().sendEvent(new Event("loadTitle", response));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setChat(UUID chatId) {
        this.chatId = chatId;
    }

}
