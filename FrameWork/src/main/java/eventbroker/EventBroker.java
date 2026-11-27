package eventbroker;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

final public class EventBroker implements Runnable {

    private final Set<EventListener> generic_listeners = new HashSet<>();
    private final Map<String, HashSet<EventListener>> typed_listeners = new HashMap<>();
    private final static EventBroker broker = new EventBroker();

    private static final class QueueItem {
        Event order;
        EventPublisher source;
        public QueueItem(Event o, EventPublisher s) {
            order = o;
            source = s;
        }
    }
    private final Object queue_lock = new Object();
    private final LinkedList<QueueItem> queue = new LinkedList<>();

    private EventBroker() {
    }

    public static EventBroker getEventBroker() {
        return broker;
    }

    public void addEventListener(EventListener s) {
        generic_listeners.add(s);
    }
    public void addEventListener(String type, EventListener s) {
        HashSet<EventListener> listeners = typed_listeners.getOrDefault(type, new HashSet<>());
        listeners.add(s);
        typed_listeners.put(
                type,
                listeners
        );
    }

    public void removeEventListener(EventListener s) {
        generic_listeners.remove(s);
        for (String type : typed_listeners.keySet()) {
            typed_listeners.get(type).remove(s);
        }
    }

    void addEvent(EventPublisher source, Event e) {
        while(!running);
        synchronized (queue_lock) {
            queue.add(new QueueItem(e, source));
            queue_lock.notifyAll();
        }
    }

    private void process(EventPublisher source, Event e) {
        for (EventListener l : generic_listeners) {
            if (l != source) {
                l.handleEvent(e); // prevent loops !
            }
        }
        for (EventListener l : typed_listeners.getOrDefault(e.getType(), new HashSet<>())) {
            if (l != source) {
                l.handleEvent(e);
            }
        }
    }

    private volatile boolean running = false;
    @Override
    public void run() {
        while (running) {
            QueueItem item;
            synchronized (queue_lock) {
                if (queue.isEmpty()) {
                    try {
                        queue_lock.wait();
                    }
                    catch(InterruptedException _) {}
                    continue;
                }
                else {
                    item = queue.pollFirst();
                }
            }
            process(item.source, item.order);
        }
        // Cleanup
        boolean isEmpty;
        synchronized (queue_lock) {
            isEmpty = queue.isEmpty();
        }
        while (!isEmpty){
            QueueItem item;
            synchronized (queue_lock) {
                item = queue.pollFirst();
                isEmpty = queue.isEmpty();
            }
            if (item != null) {
                process(item.source, item.order);
            }
        }
    }

    ExecutorService threadpool = Executors.newCachedThreadPool();
    public void start() {
        System.out.println("Starting broker...");
        running = true;
        for(int i=0;i<15;i++){
            Runnable task = new Thread(this);
            threadpool.execute(task);
        }
        System.out.println("Broker started");
    }
    public void stop() {
        System.out.println("Stopping broker...");
        running = false;
        synchronized (queue_lock) {
            queue_lock.notifyAll();
        }
        threadpool.close();
        System.out.println("Broker stopped");
    }
}
