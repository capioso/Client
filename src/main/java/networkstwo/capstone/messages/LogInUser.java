package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogInUser extends GetUser {
    @JsonProperty("password")
    private String password;

    public LogInUser(String operation, String username, String password) {
        super(operation, username);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
