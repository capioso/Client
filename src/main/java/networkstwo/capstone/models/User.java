package networkstwo.capstone.models;

public class User {
    private static String token;
    private static String username;
    private static String email;
    private static String password;

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

    public static String getPassword() {
        return User.password;
    }

    public static void setPassword(String password) {
        User.password = password;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        User.username = username;
    }
}
