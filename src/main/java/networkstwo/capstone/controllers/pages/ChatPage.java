package networkstwo.capstone.controllers.pages;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import networkstwo.capstone.App;
import networkstwo.capstone.controllers.views.ChatView;
import networkstwo.capstone.controllers.views.ContactView;
import networkstwo.capstone.controllers.stages.CreateChatView;
import networkstwo.capstone.messages.GetChats;
import networkstwo.capstone.messages.GetSingleChat;
import networkstwo.capstone.models.*;
import networkstwo.capstone.services.EventBus;
import networkstwo.capstone.services.MessageSender;

import java.util.*;

import static networkstwo.capstone.services.UserServices.updateChatById;
import static networkstwo.capstone.utils.ScreenUtils.showLittleStage;

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
                        boolean isGroup = newEvent.body().path("isGroup").asBoolean();
                        User.getChats().add(new Chat(chatId, chatTitle, isGroup));
                        addContactView(chatId, chatTitle);
                    } catch (Exception e) {
                        System.out.println("Problems with event bus: " + e.getMessage());
                    }
                });
            }
            if ("messageUpdate".equals(newEvent.type())) {
                try {
                    JsonNode item = newEvent.body();

                    String chatId = item.get("chatId").asText();
                    String messageId = item.get("messageId").asText();
                    String username = item.get("usernameSender").asText();
                    String content = item.get("content").asText();

                    Chat chatFromMessage = User.getChats().stream()
                            .filter(chat -> chat.getId().equals(UUID.fromString(chatId)))
                            .findFirst()
                            .orElse(null);

                    if (chatFromMessage != null) {
                        chatFromMessage.getMessages().add(new Message(UUID.fromString(messageId), username, content));
                        EventBus.getInstance().sendEvent(new Event("loadMessage", newEvent.body()));
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            if ("groupUpdate".equals(newEvent.type())) {
                Platform.runLater(() -> {
                    try {
                        JsonNode node = newEvent.body();
                        UUID chatId = UUID.fromString(node.get("chatId").asText());
                        String chatTitle = node.get("title").asText();
                        boolean isGroup = newEvent.body().path("isGroup").asBoolean();
                        updateChatById(chatId, chatTitle, isGroup);
                        User.getChats().add(new Chat(chatId, chatTitle, isGroup));
                        reLoadContacts();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                });

            }
        });
    }

    @FXML
    void addChatPressed(MouseEvent event) {
        try {
            UUID chatId = openUsernameView();
            if (chatId != null) {
                String newTitle = getTitleByChatId(chatId.toString());
                if (newTitle != null) {
                    User.getChats().add(new Chat(chatId, newTitle, false));
                    addContactView(chatId, newTitle);
                } else {
                    System.out.println("Problem adding chat");
                }
            } else {
                throw new RuntimeException("Chat Id is null");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void updateUserTitles() throws Exception {
        JsonNode response = MessageSender.getResponse(new GetChats(User.getToken(), Operation.GET_CHATS.name()));
        String title = response.get("title").asText();

        if (title.equals("message")) {
            JsonNode bodyNode = response.path("body");
            for (JsonNode item : bodyNode) {
                String chatId = item.path("chatId").asText();
                String chatTitle = item.path("title").asText();
                boolean isGroup = item.path("isGroup").asBoolean();
                User.getChats().add(new Chat(UUID.fromString(chatId), chatTitle, isGroup));
            }
            reLoadContacts();
        }
    }

    private String getTitleByChatId(String chatId) throws Exception {
        UUID idConverted = UUID.fromString(chatId);
        JsonNode newResponse = MessageSender.getResponse(
                new GetSingleChat(User.getToken(), Operation.GET_SINGLE_CHAT.name(), idConverted)
        );
        if (newResponse.get("title").asText().equals("message")) {
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
        FXMLLoader usernameDialog = new FXMLLoader(App.class.getResource("stages/CreateChatStage.fxml"));
        showLittleStage("Enter Username from user", new Scene(usernameDialog.load()));
        CreateChatView dialogController = usernameDialog.getController();
        return dialogController.getData();
    }

    @FXML
    void settingsPressed(MouseEvent event) {
        User.getChats().forEach(chat -> {
            System.out.println(chat.getId() + " | " + chat.getTitle());
        });
    }
}
