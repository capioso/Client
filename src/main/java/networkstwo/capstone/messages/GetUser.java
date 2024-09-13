package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUser extends Message {
    @JsonProperty("username")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
