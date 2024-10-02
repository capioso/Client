package networkstwo.capstone.controllers.views;

import javafx.fxml.FXML;
import javafx.scene.text.Font;
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

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/RobotoCondensed-MediumItalic.ttf"), 10);
        usernameTitle.setFont(titleFont);
        dateTimeText.setFont(titleFont);
        Font bodyFont = Font.loadFont(getClass().getResourceAsStream("/fonts/RobotoCondensed-Medium.ttf"), 12);
        messageBody.setFont(bodyFont);
    }

    public void setMessageBody(String messageBody) {
        this.messageBody.setText(messageBody);
    }

    public void setUsernameTitle(String usernameTitle){
        this.usernameTitle.setText(usernameTitle);
    }

    public void setDateTime(ZonedDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm");
        String formattedDate = dateTime.format(formatter);
        this.dateTimeText.setText(formattedDate);
    }
}
