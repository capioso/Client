package networkstwo.capstone.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static networkstwo.capstone.utils.Screen.changeScreen;
import static networkstwo.capstone.utils.Screen.showAlert;

public class SignInPage {
    @FXML
    private TextField emailBox;

    @FXML
    private TextField passwordBox;

    @FXML
    private TextField usernameBox;

    @FXML
    private Text title;

    @FXML
    void cancelPressed(MouseEvent event) {
        Stage stage = (Stage) title.getScene().getWindow();
        changeScreen(stage, "LogInPage.fxml");
    }

    @FXML
    void createPressed(MouseEvent event) {
        String username = usernameBox.getText();
        String message = String.format("Welcome %s, nice to see you here.", username);
        showAlert("User successfully created!", message);
        Stage stage = (Stage) title.getScene().getWindow();
        changeScreen(stage, "LogInPage.fxml");
    }

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/IrishGrover-Regular.ttf"), 30);
        title.setFont(titleFont);
    }

    boolean checkUsername(String username) {
        return true;
    }
}
