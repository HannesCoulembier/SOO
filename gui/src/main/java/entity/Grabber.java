//package entity;
//
//import org.json.JSONObject;
//import eventbroker.Event;
//import eventbroker.EventBroker;
//import eventbroker.EventListener;
//import network.Network;
//
//import java.beans.PropertyChangeListener;
//import java.beans.PropertyChangeSupport;
//import java.io.IOException;
//import java.net.InetAddress;
//
//
//public class Grabber implements EventListener {
//
//    private Event event = null;
//    private PropertyChangeSupport support = null;
//
//    public Grabber() {
//		// Create your PropertyChangeSupport
//		// Register with the EventBroker
//    }
//
//    public void handleEvent(Event e){
//        System.out.println("GRABBER through call of handleEvent: " + e.getType() + " " + e.getPayload() + "");
//		analyseEvent(e);
//        setEvent(e);
//    }
//
//    public static void main(String[] args) throws IOException {
//        Grabber grabber = new Grabber();
//        EventBroker.getEventBroker().start();
//        Network network = new Network(2024);
//        network.connect(InetAddress.getLocalHost(), 1024);
//
//    }
//
//	// This method analyses an incoming event and registers its content in an inventory to keep track of the devices
//	// (entitiez) that have been detected in the energy platform.
//    private void analyseEvent(Event e) {
//        JSONObject obj = new JSONObject(e.getPayload());
//        String id = (String)obj.get("id");
//        Entity entity = new Entity();
//        entity.setId(id);
//        Inventory.getInstance().addEntity(entity);
//    }
//
//
//    public void addPropertyChangeListener(PropertyChangeListener pcl) {
//        support.addPropertyChangeListener(pcl);
//    }
//
//    public void removePropertyChangeListener(PropertyChangeListener pcl) {
//        support.removePropertyChangeListener(pcl);
//    }
//
//    public void setEvent(Event value) {
//		// Launcgh a PropertyChangeEvent
//    }
//}
