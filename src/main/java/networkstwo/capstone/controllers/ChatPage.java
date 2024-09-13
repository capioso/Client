package networkstwo.capstone.controllers;

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

public class ChatPage {
    @FXML
    private Button GroupsButton;

    @FXML
    private VBox chatsBox;

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
            FXMLLoader usernameDialog = new FXMLLoader(App.class.getResource("UsernameDialog.fxml"));
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Enter Username");
            dialogStage.setScene(new Scene(usernameDialog.load()));
            dialogStage.setMinWidth(300);
            dialogStage.setMaxWidth(300);
            dialogStage.setWidth(300);
            dialogStage.setMinHeight(120);
            dialogStage.setMaxHeight(120);
            dialogStage.setHeight(120);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.showAndWait();
            UsernameDialog dialogController = usernameDialog.getController();
            String username = dialogController.getUsername();
            if (!username.isEmpty()) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ContactView.fxml"));
                AnchorPane pane = fxmlLoader.load();
                ContactView controller = fxmlLoader.getController();
                controller.setUsername(username);
                chatsBox.getChildren().add(pane);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void chatsPressed(MouseEvent event) {

    }

    @FXML
    void groupsPressed(MouseEvent event) {

    }

    @FXML
    void settingsPressed(MouseEvent event) {

    }
}
