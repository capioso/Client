package networkstwo.capstone;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import networkstwo.capstone.services.ServerConnection;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static networkstwo.capstone.utils.Screen.changeScreen;
import static networkstwo.capstone.utils.Screen.showAlert;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        changeScreen(stage, "WelcomePage.fxml");

        ServerConnection serverConnection = ServerConnection.getInstance();

        CompletableFuture<Boolean> pingFuture = CompletableFuture.supplyAsync(serverConnection::ping);

        pingFuture.thenAccept(isConnected -> Platform.runLater(() -> {
            if (isConnected) {
                System.out.println("Connected to server");
                changeScreen(stage, "LogInPage.fxml");
            } else {
                showAlert("Server Error", "Could not connect to server");
                stage.close();
            }
        })).exceptionally(ex -> {
            Platform.runLater(() -> {
                System.out.println("Error trying to connect to server");
                System.out.println(Arrays.toString(ex.getStackTrace()));
            });
            return null;
        });
    }

    public static void main(String[] args) {
        launch();
    }
}