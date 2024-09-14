package networkstwo.capstone.controllers.views;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ChatView {

    @FXML
    private AnchorPane basePane;

    @FXML
    private VBox messagesBox;

    @FXML
    private ImageView photo;

    @FXML
    private TextField textField;

    @FXML
    private Text titleText;

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Itim-Regular.ttf"), 22);
        titleText.setFont(titleFont);
        Font textFieldFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-SemiBoldItalic.ttf"), 13);
        textField.setFont(textFieldFont);
    }

    @FXML
    void attachPressed(MouseEvent event) {

    }

    @FXML
    void enterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            System.out.println(textField.getText());
            textField.setText("");
        }
    }

    @FXML
    void headerPressed(MouseEvent event) {

    }

    public void setUsername(String username) {
        titleText.setText(username);
    }

}
