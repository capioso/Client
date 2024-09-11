package networkstwo.capstone;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import networkstwo.capstone.services.ServerConnection;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

import static networkstwo.capstone.utils.Screen.changeScreen;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        changeScreen(stage, "WelcomePage.fxml");

        ServerConnection serverConnection = new ServerConnection("34.130.54.17", 10852);

        CompletableFuture<Boolean> pingFuture = CompletableFuture.supplyAsync(serverConnection::ping);

        pingFuture.thenAccept(isConnected -> Platform.runLater(() -> {
            if (isConnected) {
                System.out.println("Connected to server");
                changeScreen(stage, "LogInPage.fxml");
            } else {
                System.out.println("Could not connect to server");
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