package networkstwo.capstone.controllers.views;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
import networkstwo.capstone.controllers.stages.CreateGroupView;
import networkstwo.capstone.messages.GetMessagesByChat;
import networkstwo.capstone.messages.SendMessage;
import networkstwo.capstone.models.Chat;
import networkstwo.capstone.models.Message;
import networkstwo.capstone.models.Operation;
import networkstwo.capstone.models.User;
import networkstwo.capstone.services.EventBus;
import networkstwo.capstone.services.MessageSender;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static networkstwo.capstone.utils.ScreenUtils.showLittleStage;

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

    private final List<UUID> messagesIds = new ArrayList<>();

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Itim-Regular.ttf"), 22);
        titleText.setFont(titleFont);
        Font textFieldFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Poppins-SemiBoldItalic.ttf"), 13);
        textField.setFont(textFieldFont);

        EventBus.getInstance().addListener((observable, oldEvent, newEvent) -> {
            if ("loadMessage".equals(newEvent.type())) {
                Platform.runLater(() -> {
                    try {
                        JsonNode item = newEvent.body();

                        String messageId = item.get("messageId").asText();

                        Message messageToAdd = thisChat.getMessages().stream()
                                .filter(message -> message.getId().equals(UUID.fromString(messageId)))
                                .findFirst()
                                .orElse(null);

                        if (messageToAdd != null){
                            if (!messagesIds.contains(messageToAdd.getId())){
                                messagesIds.add(messageToAdd.getId());
                                if (messageToAdd.getSender().equals(User.getUsername())){
                                    addMessageView(true, User.getUsername(),messageToAdd.getBinaryContent());
                                }else{
                                    addMessageView(false, messageToAdd.getSender(), messageToAdd.getBinaryContent());
                                }
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
    void promoteChatPressed(MouseEvent event) {
        try {
            FXMLLoader createViewFxml = new FXMLLoader(App.class.getResource("stages/CreateGroupStage.fxml"));
            showLittleStage("Enter title from chat & username to add", new Scene(createViewFxml.load()));
            CreateGroupView controller = createViewFxml.getController();
            controller.setChat(chatId);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void enterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            String content = textField.getText();
            try {
                byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
                String binaryContent = Arrays.toString(bytes);

                SendMessage sendMessage = new SendMessage(User.getToken(), Operation.SEND_MESSAGE.name(), chatId, binaryContent);
                JsonNode response = MessageSender.getResponse(sendMessage);

                String title = response.get("title").asText();
                String body = response.get("body").asText();

                if (title.equals("message")) {
                    UUID messageId = UUID.fromString(body);
                    if (thisChat != null){
                        thisChat.getMessages().add(new Message(messageId, User.getUsername(), binaryContent));
                        addMessageView(true, User.getUsername(), binaryContent);
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
        String[] byteStrings = binaryContent.substring(1, binaryContent.length() - 1).split(", ");
        byte[] receivedBytes = new byte[byteStrings.length];

        for (int i = 0; i < byteStrings.length; i++) {
            receivedBytes[i] = Byte.parseByte(byteStrings[i].trim());
        }

        String decodedContent = new String(receivedBytes, StandardCharsets.UTF_8);

        FXMLLoader messageView;
        if (isOwn) {
            messageView = new FXMLLoader(App.class.getResource("views/MessageView.fxml"));
        } else {
            messageView = new FXMLLoader(App.class.getResource("views/OtherMessageView.fxml"));
        }
        AnchorPane anchorPane = messageView.load();

        MessageView controller = messageView.getController();
        controller.setUsernameTitle(username);
        controller.setMessageBody(decodedContent);
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

    private void loadMessages() {
        if (thisChat.getMessages().isEmpty()){
            GetMessagesByChat getMessage = new GetMessagesByChat(User.getToken(), Operation.GET_MESSAGES_BY_CHAT.name(), chatId);
            JsonNode response = MessageSender.getResponse(getMessage);

            if (response.get("title").asText().equals("message")) {
                JsonNode body = response.get("body");
                for (JsonNode message : body) {
                    System.out.println(message);
                    UUID messageId = UUID.fromString(message.path("messageId").asText());
                    String senderTitle = message.path("sender").asText();
                    String content = message.path("content").asText();
                    try {
                        thisChat.getMessages().add(new Message(messageId, senderTitle, content));
                        addMessageView(senderTitle.equals(User.getUsername()), senderTitle,content);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }
        }else {
            try {
                thisChat.getMessages().forEach(message -> {
                    try {
                        addMessageView(message.getSender().equals(User.getUsername()), message.getSender(),message.getBinaryContent());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
