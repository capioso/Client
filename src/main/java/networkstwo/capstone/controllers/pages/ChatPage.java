package networkstwo.capstone.controllers.pages;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
import networkstwo.capstone.messages.GetChat;
import networkstwo.capstone.models.Operation;
import networkstwo.capstone.models.User;
import networkstwo.capstone.services.EventBus;
import networkstwo.capstone.services.MessageSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        ChangeListener<String> eventBusListener = (obs, oldMessage, newMessage) -> {
            Platform.runLater(() -> {
                try {
                    User.getTitles().add(newMessage);
                    addContactView(newMessage);
                } catch (Exception e) {
                    System.out.println("Problems with event bus: " + e.getMessage());
                }
            });
        };
        EventBus.getInstance().messageProperty().addListener(eventBusListener);
    }

    @FXML
    void addChatPressed(MouseEvent event) {
        try {
            String chatCreated = openUsernameView();
            if (chatCreated.isBlank() || chatCreated.isEmpty()) {
                return;
            }
            addContactView(chatCreated);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateUserTitles() throws Exception{
        GetChat getChatMessage = new GetChat(Operation.GET_CHAT.name(), User.getToken());
        JsonNode response = MessageSender.getResponse(getChatMessage);
        String title = response.get("title").asText();
        String body = response.get("body").asText();
        if (title.equals("message")) {
            List<String> titles = stringToList(body);
            User.setTitles(titles);
            reLoadContacts();
        }
    }

    public void reLoadContacts() throws Exception {
        chatsBox.getChildren().clear();
        User.getTitles().forEach(newTitle -> {
            try {
                addContactView(newTitle);
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

    private void addContactView(String title) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("views/ContactView.fxml"));
        AnchorPane pane = fxmlLoader.load();
        ContactView controller = fxmlLoader.getController();
        controller.setTitle(title);
        pane.setOnMouseClicked(newEvent -> {
            try {
                FXMLLoader chatViewFxml = new FXMLLoader(App.class.getResource("views/ChatView.fxml"));
                AnchorPane anchorPane = chatViewFxml.load();
                ChatView controllerView = chatViewFxml.getController();
                controllerView.setTitle(title);
                chatPane.getChildren().setAll(anchorPane);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        });
        chatsBox.getChildren().add(pane);
    }

    private String openUsernameView() throws Exception {
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

    }
}
