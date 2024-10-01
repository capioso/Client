package networkstwo.capstone.utils;

public class StringValidationUtils {
    public static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static boolean validateUsername(String username) {
        if (isBlank(username)) {
            return false;
        }
        if (username.length() < 4 || username.length() > 16) {
            return false;
        }
        return username.matches("[a-zA-Z]+");
    }

    public static boolean validatePassword(String password) {
        if (isBlank(password)) {
            return false;
        }
        if (password.length() < 6 || password.length() > 16) {
            return false;
        }
        return password.matches("[a-zA-Z0-9!@#$%^&*]+");
    }

    public static boolean validateEmail(String email) {
        if (isBlank(email)) {
            return false;
        }
        if (email.length() > 50) {
            return false;
        }
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean validateTitle(String title) {
        if (isBlank(title)) {
            return false;
        }
        if (title.length() < 3 || title.length() > 16) {
            return false;
        }
        return title.matches("[a-zA-Z0-9 ]+");
    }
}
