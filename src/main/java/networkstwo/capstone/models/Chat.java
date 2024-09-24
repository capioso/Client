package networkstwo.capstone.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Chat {
    private UUID id;
    private String title;

    private final List<Message> messages = new ArrayList<>();

    public Chat(UUID id, String title) {
        this.id = id;
        this.title = title;
    }


    public List<Message> getMessages() {
        return messages;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
