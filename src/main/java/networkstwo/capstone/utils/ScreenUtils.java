package networkstwo.capstone.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;
import networkstwo.capstone.App;

public class ScreenUtils {

    public static void centerOnScreen(Stage stage) {
        javafx.stage.Screen screen = javafx.stage.Screen.getPrimary();
        double screenWidth = screen.getVisualBounds().getWidth();
        double screenHeight = screen.getVisualBounds().getHeight();

        double stageWidth = stage.getWidth();
        double stageHeight = stage.getHeight();

        double x = (screenWidth - stageWidth) / 2;
        double y = (screenHeight - stageHeight) / 2;

        stage.setX(x);
        stage.setY(y);
    }

    public static void changeScreen(Stage stage, String fxml) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("pages/" + fxml));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            stage.setTitle("CapChat");
            stage.setScene(scene);
            stage.resizableProperty().setValue(Boolean.FALSE);
            stage.show();

            stage.widthProperty().addListener((observable, oldValue, newValue) -> centerOnScreen(stage));
            stage.heightProperty().addListener((observable, oldValue, newValue) -> centerOnScreen(stage));

            stage.sizeToScene();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Information");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void showLittleStage(String title, Scene scene) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle(title);
        dialogStage.setScene(scene);
        dialogStage.setMinWidth(300);
        dialogStage.setMaxWidth(300);
        dialogStage.setWidth(300);
        dialogStage.setMinHeight(160);
        dialogStage.setMaxHeight(160);
        dialogStage.setHeight(160);
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.showAndWait();
    }
}
