package networkstwo.capstone.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import networkstwo.capstone.messages.SignInUser;
import networkstwo.capstone.services.ResponseServer;
import networkstwo.capstone.utils.Validator;

import static networkstwo.capstone.utils.Screen.changeScreen;

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
        try {
            String username = usernameBox.getText();
            String password = passwordBox.getText();
            String email = emailBox.getText();
            if (Validator.validateUsername(username) && Validator.validatePassword(password) && Validator.validateEmail(email)) {
                SignInUser signInMessage = new SignInUser();
                signInMessage.setOperation("CREATE_USER");
                signInMessage.setEmail(email);
                signInMessage.setUsername(username);
                signInMessage.setPassword(password);
                String response = ResponseServer.getResponse(signInMessage);
                System.out.println("Server response: " + response);
                Stage stage = (Stage) title.getScene().getWindow();
                changeScreen(stage, "LogInPage.fxml");
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/IrishGrover-Regular.ttf"), 30);
        title.setFont(titleFont);
    }
}
