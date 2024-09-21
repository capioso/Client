package networkstwo.capstone.models;

import java.util.UUID;

public class Message {
    private final UUID id;
    private final String sender;
    private final String binaryContent;

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
