package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetChat extends Message {
    @JsonProperty("token")
    private String token;

    public GetChat(String operation, String token) {
        super(operation);
        this.token = token;
    }
    public String getToken() {
        return token;
    }
}
