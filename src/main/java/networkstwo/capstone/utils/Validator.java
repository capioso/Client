package networkstwo.capstone.utils;

public class Validator {
    public static boolean validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }
        if (username.length() < 4 || username.length() > 16) {
            return false;
        }
        return username.matches("[a-zA-Z]+");
    }

    public static boolean validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            return false;
        }
        if (password.length() < 8 || password.length() > 16) {
            return false;
        }
        return password.matches("[a-zA-Z0-9!@#$%^&*]+");
    }

    public static boolean validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        if (email.length() > 50) {
            return false;
        }
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }
}
