package networkstwo.capstone.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static String token;
    private static String username;
    private static String email;
    private static List<Chat> chats = new ArrayList<>();



    public static List<Chat> getChats() {
        return chats;
    }


    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        User.email = email;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        User.token = token;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }
}
