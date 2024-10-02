package networkstwo.capstone.models;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public class Message {
    private final UUID id;
    private final String sender;
    private final String binaryContent;
    private final ZonedDateTime timestamp;

    public Message(UUID id, String sender, String binaryContent, ZonedDateTime timestamp) {
        this.id = id;
        this.sender = sender;
        this.binaryContent = binaryContent;
        this.timestamp = timestamp;
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

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
