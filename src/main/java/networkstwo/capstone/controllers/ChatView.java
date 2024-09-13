package networkstwo.capstone.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.input.MouseEvent;

public class ChatView {
    @FXML
    private Text usernameText;

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Itim-Regular.ttf"), 15);
        usernameText.setFont(titleFont);
    }

    @FXML
    void openChat(MouseEvent event) {

    }
}
