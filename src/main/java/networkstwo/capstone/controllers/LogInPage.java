package networkstwo.capstone.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import networkstwo.capstone.messages.LogInUser;
import networkstwo.capstone.services.ResponseServer;
import networkstwo.capstone.utils.Validator;

import static networkstwo.capstone.utils.Screen.changeScreen;

public class LogInPage {
    @FXML
    private TextField passwordBox;

    @FXML
    private Text title;

    @FXML
    private TextField usernameBox;

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/IrishGrover-Regular.ttf"), 30);
        title.setFont(titleFont);
    }

    @FXML
    void enterPressed(MouseEvent event) {
        try {
            String username = usernameBox.getText();
            String password = passwordBox.getText();
            if (Validator.validateUsername(username) && Validator.validatePassword(password)) {
                LogInUser logInMessage = new LogInUser();
                logInMessage.setOperation("LOGIN_USER");
                logInMessage.setUsername(username);
                logInMessage.setPassword(password);
                String response = ResponseServer.getResponse(logInMessage);
                System.out.println("Server response: " + response);
                System.out.println("WELCOME");
            }else {
                throw new Exception("Bad Username or Password");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void registerPressed(MouseEvent event) {
        Stage stage = (Stage) title.getScene().getWindow();
        changeScreen(stage, "SignInPage.fxml");
    }

}