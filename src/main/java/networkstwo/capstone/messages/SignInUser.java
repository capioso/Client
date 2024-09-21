package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SignInUser (
        @JsonProperty("operation") String operation,
        @JsonProperty("username") String username,
        @JsonProperty("email") String email,
        @JsonProperty("password") String password

) {
}
