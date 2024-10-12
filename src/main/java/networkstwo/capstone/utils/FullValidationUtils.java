package networkstwo.capstone.utils;

import networkstwo.capstone.models.User;

import java.util.List;

import static networkstwo.capstone.services.UserServices.getTitlesWith;
import static networkstwo.capstone.utils.StringValidationUtils.validateTitle;
import static networkstwo.capstone.utils.StringValidationUtils.validateUsername;

public class FullValidationUtils {
    public static void acceptUsername(String username) throws Exception {
        if (!validateUsername(username)) {
            throw new RuntimeException("Bad username");
        }
        if (username.equals(User.getUsername())){
            throw new RuntimeException("You can not add yourself");
        }
    }

    public static void acceptTitle(String title) throws Exception {
        if (!validateTitle(title)) {
            throw new RuntimeException("Bad title");
        }
        List<String> filteredTitles = getTitlesWith(title);

        if (!filteredTitles.isEmpty()){
            throw new Exception("Title already added");
        }
    }
}
