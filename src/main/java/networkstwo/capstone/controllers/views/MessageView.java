package networkstwo.capstone.controllers.views;

import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import networkstwo.capstone.models.User;

public class MessageView {

    @FXML
    private Text messageBody;

    @FXML
    private Text usernameTitle;

    public void setMessageBody(String messageBody) {
        this.messageBody.setText(messageBody);
    }

    public void setUsernameTitle(String usernameTitle){
        this.usernameTitle.setText(usernameTitle);
    }
}
