package networkstwo.capstone.controllers.stages;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static networkstwo.capstone.utils.FullValidationUtils.acceptTitle;
import static networkstwo.capstone.utils.FullValidationUtils.acceptUsername;

public class CreateGroupView {
    @FXML
    private TextField titleText;

    @FXML
    private TextField usernameText;

    private final String[] data = new String[2];

    @FXML
    void cancelPressed(MouseEvent event) {
        Stage stage = (Stage) titleText.getScene().getWindow();
        stage.close();
    }

    @FXML
    void okPressed(MouseEvent event) {
        try {
            String username = usernameText.getText();
            String title = titleText.getText();
            acceptUsername(username);
            acceptTitle(title);

            data[0] = username;
            data[1] = title;

            Stage stage = (Stage) titleText.getScene().getWindow();
            stage.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public String[] getData(){
        return data;
    }

}
