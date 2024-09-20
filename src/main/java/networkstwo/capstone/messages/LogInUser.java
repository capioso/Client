package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LogInUser (
        @JsonProperty("operation") String operation,
        @JsonProperty("username") String username,
        @JsonProperty("password") String password
) {
}
