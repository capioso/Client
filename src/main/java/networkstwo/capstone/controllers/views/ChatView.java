package networkstwo.capstone.controllers.views;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import networkstwo.capstone.App;
import networkstwo.capstone.messages.GetChat;
import networkstwo.capstone.messages.SendMessage;
import networkstwo.capstone.models.Operation;
import networkstwo.capstone.models.User;
import networkstwo.capstone.services.MessageSender;

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
            String content = textField.getText();
            try {
                SendMessage sendMessage = new SendMessage(Operation.SEND_MESSAGE.name(), User.getToken(), content, titleText.getText());
                JsonNode response = MessageSender.getResponse(sendMessage);
                String title = response.get("title").asText();
                if (title.equals("message")){
                    addMessageView(true, User.getUsername(), content);
                    textField.setText("");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void addMessageView(boolean isOwn, String username, String content) throws Exception{
        FXMLLoader messageView;
        if (isOwn){
            messageView = new FXMLLoader(App.class.getResource("views/MessageView.fxml"));
        }else{
            messageView = new FXMLLoader(App.class.getResource("views/OtherMessageView.fxml"));
        }
        AnchorPane anchorPane = messageView.load();

        MessageView controller = messageView.getController();
        controller.setUsernameTitle(username);
        controller.setMessageBody(content);
        messagesBox.getChildren().add(anchorPane);
    }

    @FXML
    void headerPressed(MouseEvent event) {

    }

    public void setTitle(String username) {
        titleText.setText(username);
    }

}
