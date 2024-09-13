package networkstwo.capstone.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ContactView {
    @FXML
    private Text usernameText;

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Itim-Regular.ttf"), 15);
        usernameText.setFont(titleFont);
    }

    public void setUsername(String username) {
        usernameText.setText(username);
    }
}
