package networkstwo.capstone.models;

import java.util.*;

public class Chat {
    private UUID id;
    private String title;
    private final Set<Message> messages = new HashSet<>();

    public Chat(UUID id, String title) {
        this.id = id;
        this.title = title;
    }

    public Set<Message> getMessages() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
