package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogInUser extends Message {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
