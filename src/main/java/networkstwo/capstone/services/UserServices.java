package networkstwo.capstone.services;

import networkstwo.capstone.models.Chat;
import networkstwo.capstone.models.User;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserServices {
    public static List<String> getTitlesWith(String titleToFind){
        return User.getChats()
                .stream()
                .map(Chat::getTitle)
                .filter(title -> Objects.equals(title, titleToFind))
                .toList();
    }

    public static void updateTitleById(UUID id, String newTitle){
        User.getChats()
                .stream()
                .filter(chatToUpdate -> chatToUpdate.getId().equals(id))
                .findFirst()
                .ifPresent(chat -> chat.setTitle(newTitle));
    }
}
