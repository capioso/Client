package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogInUser extends Message {
    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    public LogInUser(String operation, String username, String password) {
        super(operation);
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
