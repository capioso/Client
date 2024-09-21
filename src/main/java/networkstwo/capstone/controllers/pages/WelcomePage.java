package networkstwo.capstone.controllers.pages;

import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class WelcomePage {

    @FXML
    private Text messageText;

    @FXML
    private Text titleText;

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/IrishGrover-Regular.ttf"), 64);
        titleText.setFont(titleFont);
        Font messageFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Itim-Regular.ttf"), 21);
        messageText.setFont(messageFont);
    }
}
