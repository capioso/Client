package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateChat extends GetChat {
    @JsonProperty("title")
    private String title;

    @JsonProperty("username")
    private String username;

    public CreateChat(String operation, String title, String username, String token) {
        super(operation, token);
        this.title = title;
        this.username = username;
    }

    public String getUsername() {return username;}

    public String getTitle() {
        return title;
    }
}
