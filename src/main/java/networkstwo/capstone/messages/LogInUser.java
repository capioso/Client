package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogInUser extends GetUser {
    @JsonProperty("password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
