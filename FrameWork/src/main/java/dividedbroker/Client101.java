package dividedbroker;

import alarmevent.EmergencyCallCenter;
import eventbroker.EventBroker;
import network.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class Client101 {

    public static void main(String[] args) throws IOException {

        EventBroker broker = EventBroker.getEventBroker();
        broker.start();

        Network network = new Network();
        network.connect(InetAddress.getLocalHost(), 1024);

        EmergencyCallCenter callCenter101 = new EmergencyCallCenter("101");

        callCenter101.incomingCall("assault", "Veldstraat");
        callCenter101.incomingCall("assault", "Overpoortstraat");

        while (!new BufferedReader(new InputStreamReader(System.in)).readLine().equals("stop"));
        network.terminate();
    }
}
