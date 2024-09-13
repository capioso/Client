package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetUser extends Message {
    @JsonProperty("username")
    private String username;

    public GetUser(String operation, String username) {
        super(operation);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
