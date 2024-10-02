package networkstwo.capstone.controllers.views;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class MessageView {

    @FXML
    private Text messageBody;

    @FXML
    private Text usernameTitle;

    @FXML
    private Text dateTimeText;

    public void setMessageBody(String messageBody) {
        this.messageBody.setText(messageBody);
    }

    public void setUsernameTitle(String usernameTitle){
        this.usernameTitle.setText(usernameTitle);
    }

    public void setDateTime(String dateTime) {
        this.dateTimeText.setText(dateTime);
    }
}
