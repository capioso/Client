package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty("operation")
    private String operation;

    public Message(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }
}
