package networkstwo.capstone.controllers.views;

import javafx.fxml.FXML;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ContactView {
    @FXML
    private Text titleText;

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Itim-Regular.ttf"), 15);
        titleText.setFont(titleFont);
    }

    public void setTitle(String title) {
        titleText.setText(title);
    }
}
