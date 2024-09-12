package networkstwo.capstone.messages;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Message {
    @JsonProperty("operation")
    private String operation;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
