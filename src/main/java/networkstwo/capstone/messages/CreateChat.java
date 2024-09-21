package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CreateChat(
        @JsonProperty("token") String token,
        @JsonProperty("operation") String operation,
        @JsonProperty("chatId") UUID chatId,
        @JsonProperty("username") String username
) {
}