package networkstwo.capstone.controllers.stages;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static networkstwo.capstone.utils.FullValidationUtils.acceptUsername;
import static networkstwo.capstone.utils.ScreenUtils.showAlert;

public class AddUserToChatStage {
    @FXML
    private TextField usernameText;

    private String  data = "";

    @FXML
    public void initialize() throws Exception {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/RobotoCondensed-MediumItalic.ttf"), 13);
        usernameText.setFont(titleFont);
    }

    @FXML
    void cancelPressed(MouseEvent event) {
        closeWindow();
    }

    @FXML
    void okPressed(MouseEvent event) {
        try {
            String username = usernameText.getText();
            acceptUsername(username);
            this.data = username;
            closeWindow();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            usernameText.setText("");
        }
    }

    public String getData() {
        return data;
    }

    private void closeWindow (){
        Stage stage = (Stage) usernameText.getScene().getWindow();
        stage.close();
    }
}
