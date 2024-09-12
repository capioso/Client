package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SignInUser extends LogInUser {
    @JsonProperty("email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
