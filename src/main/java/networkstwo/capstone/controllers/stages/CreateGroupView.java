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

    private final String[] data = new String[2];

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

            data[0] = username;
            data[1] = title;
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String[] getData(){
        return data;
    }

}
