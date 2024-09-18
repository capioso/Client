package networkstwo.capstone.controllers.pages;

import com.fasterxml.jackson.databind.JsonNode;
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
import networkstwo.capstone.services.MessageSender;

public class ChatPage {
    @FXML
    private Button GroupsButton;

    @FXML
    private VBox chatsBox;

    @FXML
    private AnchorPane chatPane;

    @FXML
    private Button chatsButton;

    @FXML
    private Text titleText;

    @FXML
    public void initialize() {
        Font titleFont = Font.loadFont(getClass().getResourceAsStream("/fonts/IrishGrover-Regular.ttf"), 21);
        titleText.setFont(titleFont);
        Font buttonFont = Font.loadFont(getClass().getResourceAsStream("/fonts/Itim-Regular.ttf"), 10);
        GroupsButton.setFont(buttonFont);
        chatsButton.setFont(buttonFont);
    }

    @FXML
    void addChatPressed(MouseEvent event) {
        try {
            String title = openUsernameView();
            if (title.isEmpty()) {
                return;
            }
            addContactView(title);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void preLoadContacts(){
        GetChat getChatMessage = new GetChat(Operation.GET_CHAT.name(), User.getToken());
        JsonNode response = MessageSender.getResponse(getChatMessage);
        String title = response.get("title").asText();
        String body = response.get("body").asText();
        if (title.equals("message")) {
            System.out.println("BODY: " + body);
        }
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
            }catch (Exception e){
                throw new RuntimeException(e.getMessage());
            }
        });
        chatsBox.getChildren().add(pane);
    }

    private String openUsernameView() throws Exception{
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
    void chatsPressed(MouseEvent event) {

    }

    @FXML
    void groupsPressed(MouseEvent event) {

    }

    @FXML
    void settingsPressed(MouseEvent event) {
        preLoadContacts();
    }
}
