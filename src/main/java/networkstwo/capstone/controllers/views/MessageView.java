package networkstwo.capstone.controllers.views;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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

    public void setDateTime(ZonedDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH/mm");
        String formattedDate = dateTime.format(formatter);
        this.dateTimeText.setText(formattedDate);
    }
}
