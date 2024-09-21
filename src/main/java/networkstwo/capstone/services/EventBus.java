package networkstwo.capstone.services;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import networkstwo.capstone.models.Event;

public class EventBus {
    private static final EventBus instance = new EventBus();
    private final SimpleObjectProperty<Event> event = new SimpleObjectProperty<>();

    private EventBus() {}

    public static EventBus getInstance() {
        return instance;
    }

    public void sendEvent(Event event) {
        this.event.set(event);
    }

    public SimpleObjectProperty<Event> eventProperty() {
        return event;
    }

    public void addListener(ChangeListener<? super Event> listener) {
        event.addListener(listener);
    }
}
