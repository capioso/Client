package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class SendMessage extends GetChat{
    @JsonProperty("content")
    private final String content;
    @JsonProperty("chatTitle")
    private final String chatTitle;

    public SendMessage(String operation, String token, String content, String chatTitle) {
        super(operation, token);
        this.content = content;
        this.chatTitle = chatTitle;
    }

    public String getContent() {
        return content;
    }

    public String getChatTitle() {
        return chatTitle;
    }
}
