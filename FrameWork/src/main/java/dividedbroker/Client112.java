package dividedbroker;

import alarmevent.EmergencyCallCenter;
import eventbroker.EventBroker;
import network.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class Client112 {

    public static void main(String[] args) throws IOException {

        EventBroker broker = EventBroker.getEventBroker();
        broker.start();

        Network network = new Network();
        network.connect(InetAddress.getLocalHost(), 1024);

        EmergencyCallCenter callCenter112 = new EmergencyCallCenter("112");

        callCenter112.incomingCall("crash", "Plateaustraat");
        callCenter112.incomingCall("fire", "Zwijnaardse Steenweg");

        while (!new BufferedReader(new InputStreamReader(System.in)).readLine().equals("stop"));
        network.terminate();
    }
}
