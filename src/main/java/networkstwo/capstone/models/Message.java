package networkstwo.capstone.models;

import java.util.UUID;

public class Message {
    private UUID id;
    private String sender;
    private String binaryContent;

    public Message(UUID id, String sender, String binaryContent) {
        this.id = id;
        this.sender = sender;
        this.binaryContent = binaryContent;
    }

    public UUID getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getBinaryContent() {
        return binaryContent;
    }
}
