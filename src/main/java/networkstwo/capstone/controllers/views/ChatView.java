package networkstwo.capstone.controllers.views;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Platform;
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
import networkstwo.capstone.messages.GetMessagesByChat;
import networkstwo.capstone.messages.SendMessage;
import networkstwo.capstone.models.Chat;
import networkstwo.capstone.models.Message;
import networkstwo.capstone.models.Operation;
import networkstwo.capstone.models.User;
import networkstwo.capstone.services.EventBus;
import networkstwo.capstone.services.MessageSender;

import java.nio.charset.StandardCharsets;
import java.util.*;

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

    private UUID chatId;

    private Chat thisChat;

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Itim-Regular.ttf"), 22);
        titleText.setFont(titleFont);
        Font textFieldFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-SemiBoldItalic.ttf"), 13);
        textField.setFont(textFieldFont);

        EventBus.getInstance().addListener((observable, oldEvent, newEvent) -> {
            if ("loadMessage".equals(newEvent.getType())) {
                Platform.runLater(() -> {
                    try {
                        Message messageToAdd = thisChat.getMessages().stream()
                                .filter(message -> message.getId().equals(UUID.fromString(newEvent.getBody())))
                                .findFirst()
                                .orElse(null);
                        if (messageToAdd != null){
                            if (messageToAdd.getSender().equals(User.getUsername())){
                                addMessageView(true, User.getUsername(),messageToAdd.getBinaryContent());
                            }else{
                                addMessageView(false, messageToAdd.getSender(), messageToAdd.getBinaryContent());
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Problems with event bus: " + e.getMessage());
                    }
                });
            }
        });
    }

    @FXML
    void attachPressed(MouseEvent event) {

    }

    @FXML
    void enterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String content = textField.getText();
            try {
                byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
                String binaryContent = Base64.getEncoder().encodeToString(bytes);
                SendMessage sendMessage = new SendMessage(User.getToken(), Operation.SEND_MESSAGE.name(), chatId, binaryContent);
                JsonNode response = MessageSender.getResponse(sendMessage);
                String title = response.get("title").asText();
                String body = response.get("body").asText();
                if (title.equals("message")) {
                    UUID messageId = UUID.fromString(body);
                    if (thisChat != null){
                        thisChat.getMessages().add(new Message(messageId, User.getUsername(), binaryContent));
                        addMessageView(true, User.getUsername(), content);
                        textField.setText("");
                    }else{
                        throw new Exception("thisChat is null");
                    }
                }else{
                    throw new Exception(body);
                }
            } catch (Exception e) {
                System.out.println("Problem sending message: " + e.getMessage());
            }
        }
    }

    private void addMessageView(boolean isOwn, String username, String binaryContent) throws Exception {
        byte[] decodedBytes = Base64.getDecoder().decode(binaryContent);

        FXMLLoader messageView;
        if (isOwn) {
            messageView = new FXMLLoader(App.class.getResource("views/MessageView.fxml"));
        } else {
            messageView = new FXMLLoader(App.class.getResource("views/OtherMessageView.fxml"));
        }
        AnchorPane anchorPane = messageView.load();

        MessageView controller = messageView.getController();
        controller.setUsernameTitle(username);
        controller.setMessageBody(new String(decodedBytes, StandardCharsets.UTF_8));
        messagesBox.getChildren().add(anchorPane);
    }

    @FXML
    void headerPressed(MouseEvent event) {

    }

    public void setData(UUID chatId, String title) {
        this.chatId = chatId;
        titleText.setText(title);

        thisChat = User.getChats().stream()
                .filter(chat -> chat.getId().equals(this.chatId))
                .findFirst()
                .orElse(null);

        loadMessages();
    }

    public void loadMessages() {
        GetMessagesByChat getMesage = new GetMessagesByChat(User.getToken(), Operation.GET_MESSAGES_BY_CHAT.name(), chatId);
        JsonNode response = MessageSender.getResponse(getMesage);
        if (response.get("title").asText().equals("message")) {
            String body = response.get("body").asText();
            List<Message> messagesList = stringToList(body);
            messagesList.forEach(message -> {
                try {
                    if (message.getSender().equals(User.getUsername())){
                        addMessageView(true, User.getUsername(),message.getBinaryContent());
                    }else{
                        addMessageView(false, message.getSender(), message.getBinaryContent());
                    }
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            });
        }
    }

    private List<Message> stringToList(String body) {
        String[] parts = body.substring(1, body.length() - 1).split(",\\s*");

        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < parts.length; i += 3) {
            try {
                UUID id = UUID.fromString(parts[i]);
                String sender = parts[i + 1];
                String content = parts[i + 2];
                messages.add(new Message(id, sender, content));
            }catch (Exception e){
                System.out.println("Error parsing string to list of messages: " + e.getMessage());
            }
        }

        return messages;
    }
}
