package networkstwo.capstone.models;

public class Event {
    private final String type;
    private final String body;

    public Event(String type, String body) {
        this.type = type;
        this.body = body;
    }

    public String getType() {
        return type;
    }

    public String getBody() {
        return body;
    }
}
