package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignInUser extends LogInUser {
    @JsonProperty("email")
    private String email;

    public SignInUser(String operation, String username, String password, String email) {
        super(operation, username, password);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
