package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.ZonedDateTime;
import java.util.UUID;

public record SendMessage(
        @JsonProperty("token") String token,
        @JsonProperty("operation") String operation,
        @JsonProperty("chatId") UUID chatId,
        @JsonProperty("content") String content,
        @JsonProperty("timestamp") String timestamp
) {
}
