package networkstwo.capstone.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import networkstwo.capstone.messages.SignInUser;
import networkstwo.capstone.services.ResponseServer;
import networkstwo.capstone.utils.Validator;

import static networkstwo.capstone.utils.Screen.changeScreen;
import static networkstwo.capstone.utils.Screen.showAlert;

public class SignInPage {
    @FXML
    private TextField emailBox;

    @FXML
    private PasswordField passwordBox;

    @FXML
    private TextField usernameBox;

    @FXML
    private Text title;

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/IrishGrover-Regular.ttf"), 30);
        title.setFont(titleFont);
        Font fieldsFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-SemiBoldItalic.ttf"), 21);
        usernameBox.setFont(fieldsFont);
        emailBox.setFont(fieldsFont);
        passwordBox.setFont(fieldsFont);
    }

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
                showAlert(Alert.AlertType.INFORMATION, "Server response", response);
                if (response.equals("User created successfully")){
                    Stage stage = (Stage) title.getScene().getWindow();
                    changeScreen(stage, "LogInPage.fxml");
                }else{
                    throw new Exception(response);
                }
            }else{
                throw new Exception("Invalid username or password or email.");
            }
        }catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error while creating user", e.getMessage());
            usernameBox.setText("");
            emailBox.setText("");
            passwordBox.setText("");
        }
    }
}
