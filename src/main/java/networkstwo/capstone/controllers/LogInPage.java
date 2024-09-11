package networkstwo.capstone.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import networkstwo.capstone.messages.CreateUserMessage;
import networkstwo.capstone.services.ServerConnection;

import static networkstwo.capstone.utils.Screen.changeScreen;

public class LogInPage {
    @FXML
    private TextField passwordBox;

    @FXML
    private Text title;

    @FXML
    private TextField usernameBox;

    @FXML
    void enterPressed(MouseEvent event) {
        try {
            ServerConnection serverConnection = ServerConnection.getInstance();

            CreateUserMessage message = new CreateUserMessage();
            message.setOperation("CREATE_USER");
            message.setUsername("capioso");
            message.setEmail("capioso@gmail.com");
            message.setPassword("encrypted");

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(message);

            serverConnection.sendMessage(jsonMessage);

            String response = serverConnection.receiveMessage();
            if (response != null) {
                System.out.println("Server response: " + response);
            }
        } catch (Exception e) {
            System.out.println("Error pressing enter: " + e.getMessage());
        }
    }

    @FXML
    void registerPressed(MouseEvent event) {
        Stage stage = (Stage) title.getScene().getWindow();
        changeScreen(stage, "SignInPage.fxml");
    }

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/IrishGrover-Regular.ttf"), 30);
        title.setFont(titleFont);
    }

}