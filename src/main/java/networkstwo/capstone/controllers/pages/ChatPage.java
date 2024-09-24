package networkstwo.capstone.controllers.pages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import networkstwo.capstone.App;
import networkstwo.capstone.controllers.views.ChatView;
import networkstwo.capstone.controllers.views.ContactView;
import networkstwo.capstone.controllers.views.UsernameView;
import networkstwo.capstone.messages.GetChats;
import networkstwo.capstone.messages.GetSingleChat;
import networkstwo.capstone.models.*;
import networkstwo.capstone.services.EventBus;
import networkstwo.capstone.services.MessageSender;

import java.util.*;

public class ChatPage {
    @FXML
    private Text usernameLabel;

    @FXML
    private VBox chatsBox;

    @FXML
    private AnchorPane chatPane;

    @FXML
    private Text titleText;

    @FXML
    public void initialize() throws Exception {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/IrishGrover-Regular.ttf"), 21);
        titleText.setFont(titleFont);
        Font buttonFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Itim-Regular.ttf"), 17);
        usernameLabel.setFont(buttonFont);
        usernameLabel.setText("Hi " + User.getUsername() + "!");
        updateUserTitles();
        EventBus.getInstance().addListener((observable, oldEvent, newEvent) -> {
            if ("chatUpdate".equals(newEvent.type())) {
                Platform.runLater(() -> {
                    try {
                        UUID chatId = UUID.fromString(newEvent.body().path("chatId").asText());
                        String chatTitle = newEvent.body().path("title").asText();
                        User.getChats().add(new Chat(chatId, chatTitle));
                        addContactView(chatId, chatTitle);
                    } catch (Exception e) {
                        System.out.println("Problems with event bus: " + e.getMessage());
                    }
                });
            }
            if ("messageUpdate".equals(newEvent.type())){
                try {
                    for (JsonNode item : newEvent.body()){
                        System.out.println(item.toString());
                    }
                    /*
                    String chatId = newEvent.body().path("chatId").asText();
                    String messageId = newEvent.body().path("messageId").asText();
                    String username = newEvent.body().path("username").asText();
                    String content = newEvent.body().path("content").asText();

                    Chat chatFromMessage = User.getChats().stream()
                            .filter(chat -> chat.getId().equals(UUID.fromString(chatId)))
                            .findFirst()
                            .orElse(null);

                    if (chatFromMessage != null){
                        chatFromMessage.getMessages().add(new Message(UUID.fromString(messageId), username, content));
                        EventBus.getInstance().sendEvent(new Event("loadMessage", newEvent.body()));
                    }

                     */
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        });
    }

    @FXML
    void addChatPressed(MouseEvent event) {
        try {
            UUID chatId = openUsernameView();
            if (chatId != null) {
                String newTitle = getTitleByChatId(chatId.toString());
                if (newTitle != null){
                    User.getChats().add(new Chat(chatId, newTitle));
                    addContactView(chatId, newTitle);
                }else{
                    System.out.println("Problem adding chat");
                }
            }else{
                throw new RuntimeException("Chat Id is null");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUserTitles() throws Exception{
        JsonNode response = MessageSender.getResponse(new GetChats(User.getToken(), Operation.GET_CHATS.name()));
        String title = response.get("title").asText();

        if (title.equals("message")) {
            JsonNode bodyNode = response.path("body");
            for (JsonNode item : bodyNode) {
                String chatId = item.path("chatId").asText();
                String chatTitle = item.path("title").asText();
                User.getChats().add(new Chat(UUID.fromString(chatId), chatTitle));
            }
            reLoadContacts();
        }
    }

    private String getTitleByChatId(String chatId) throws Exception{
        UUID idConverted = UUID.fromString(chatId);
        JsonNode newResponse = MessageSender.getResponse(
                new GetSingleChat(User.getToken(), Operation.GET_SINGLE_CHAT.name(), idConverted)
        );
        if (newResponse.get("title").asText().equals("message")){
            return newResponse.get("body").asText();
        }
        throw new Exception("No chat recovered");
    }

    public void reLoadContacts() throws Exception {
        chatsBox.getChildren().clear();
        User.getChats().forEach(chat -> {
            try {
                addContactView(chat.getId(), chat.getTitle());
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    private List<String> stringToList(String input) {
        if (input == null || input.equals("[]")) {
            return new ArrayList<>();
        }
        String cleanInput = input.substring(1, input.length() - 1);
        return Arrays.asList(cleanInput.split("\\s*,\\s*"));
    }

    private void addContactView(UUID chatId, String title) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/ContactView.fxml"));
        AnchorPane pane = fxmlLoader.load();
        ContactView controller = fxmlLoader.getController();
        controller.setTitle(title);
        pane.setOnMouseClicked(newEvent -> {
            try {
                FXMLLoader chatViewFxml = new FXMLLoader(App.class.getResource("views/ChatView.fxml"));
                AnchorPane anchorPane = chatViewFxml.load();
                ChatView controllerView = chatViewFxml.getController();
                controllerView.setData(chatId, title);
                chatPane.getChildren().setAll(anchorPane);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
        chatsBox.getChildren().add(pane);
    }

    private UUID openUsernameView() throws Exception {
        FXMLLoader usernameDialog = new FXMLLoader(App.class.getResource("views/UsernameView.fxml"));
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Enter Chat's title & Username");
        dialogStage.setScene(new Scene(usernameDialog.load()));
        dialogStage.setMinWidth(300);
        dialogStage.setMaxWidth(300);
        dialogStage.setWidth(300);
        dialogStage.setMinHeight(160);
        dialogStage.setMaxHeight(160);
        dialogStage.setHeight(160);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.showAndWait();
        UsernameView dialogController = usernameDialog.getController();
        return dialogController.getData();
    }

    @FXML
    void settingsPressed(MouseEvent event) {
        User.getChats().forEach(chat -> {
            System.out.println(chat.getId() + " | "+ chat.getTitle());
        });
    }
}
