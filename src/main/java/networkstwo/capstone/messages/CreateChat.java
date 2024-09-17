package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateChat extends Message {
    @JsonProperty("title")
    private String title;

    @JsonProperty("username")
    private String username;

    @JsonProperty("token")
    private String token;

    public CreateChat(String operation, String title, String username, String token) {
        super(operation);
        this.title = title;
        this.username = username;
        this.token = token;
    }

    public String getUsername() {return username;}

    public String getTitle() {
        return title;
    }

    public String getToken() {
        return token;
    }
}
