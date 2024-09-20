package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SendMessage (
        @JsonProperty("token") String token,
        @JsonProperty("operation") String operation,
        @JsonProperty("chatTitle") String chatTitle,
        @JsonProperty("content") String content
) {
}
