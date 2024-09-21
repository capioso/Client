package networkstwo.capstone.controllers.pages;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import networkstwo.capstone.messages.LogInUser;
import networkstwo.capstone.models.Operation;
import networkstwo.capstone.models.User;
import networkstwo.capstone.services.MessageSender;
import networkstwo.capstone.services.ServerConnection;
import networkstwo.capstone.utils.ValidationUtils;

import static networkstwo.capstone.utils.ScreenUtils.changeScreen;
import static networkstwo.capstone.utils.ScreenUtils.showAlert;

public class LogInPage {
    @FXML
    private PasswordField passwordBox;

    @FXML
    private Text title;

    @FXML
    private TextField usernameBox;

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/IrishGrover-Regular.ttf"), 35);
        title.setFont(titleFont);
        Font fieldsFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-SemiBoldItalic.ttf"), 21);
        usernameBox.setFont(fieldsFont);
        passwordBox.setFont(fieldsFont);
    }

    @FXML
    void enterPressed(MouseEvent event) {
        try {
            String username = usernameBox.getText();
            String password = passwordBox.getText();
            if (!ValidationUtils.validateUsername(username) || !ValidationUtils.validatePassword(password)) {
                throw new Exception("Bad Username or Password");
            }
            LogInUser logInMessage = new LogInUser(Operation.LOGIN_USER.name(), username, password);
            JsonNode response = MessageSender.getResponse(logInMessage);
            String title = response.get("title").asText();
            String body = response.get("body").asText();
            if (!title.equals("token")) {
                throw new Exception("Token Lost");
            }
            User.setToken(body);
            User.setUsername(username);
            Stage stage = (Stage) this.title.getScene().getWindow();
            changeScreen(stage, "ChatPage.fxml");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error while login user", e.getMessage());
            usernameBox.setText("");
            passwordBox.setText("");
        }
    }

    @FXML
    void registerPressed(MouseEvent event) {
        Stage stage = (Stage) title.getScene().getWindow();
        changeScreen(stage, "SignInPage.fxml");
    }

}