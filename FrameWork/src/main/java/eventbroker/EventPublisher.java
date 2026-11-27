package eventbroker;

public class EventPublisher {
    protected String message;

    public void publishEvent(Event e) {
        EventBroker.getEventBroker().addEvent(this, e);
    }
}
