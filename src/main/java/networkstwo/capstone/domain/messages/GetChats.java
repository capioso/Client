package networkstwo.capstone.domain.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetChats(
        @JsonProperty("token") String token,
        @JsonProperty("operation") String operation
) {
}
