package networkstwo.capstone.services;

import javafx.beans.property.SimpleObjectProperty;

public class EventBus {
    private static final EventBus instance = new EventBus();
    private final SimpleObjectProperty<String> message = new SimpleObjectProperty<>();

    private EventBus() {}

    public static EventBus getInstance() {
        return instance;
    }

    public void sendMessage(String value) {
        message.set(value);
    }

    public SimpleObjectProperty<String> messageProperty() {
        return message;
    }
}
