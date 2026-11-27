package alarmevent;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import eventbroker.EventBroker;
import network.Network;

public class Client {

    public static void main(String[] args) throws IOException {

        EventBroker broker = EventBroker.getEventBroker();
        broker.start();

        Network network = new Network();
        network.connect(InetAddress.getLocalHost(), 1024);

        EmergencyCallCenter callCenter112 = new EmergencyCallCenter("112");
        EmergencyCallCenter callCenter101 = new EmergencyCallCenter("101");

        callCenter112.incomingCall("crash", "Plateaustraat");
        callCenter101.incomingCall("assault", "Veldstraat");
        callCenter112.incomingCall("fire", "Zwijnaardse Steenweg");
        callCenter101.incomingCall("assault", "Overpoortstraat");

        while (!new BufferedReader(new InputStreamReader(System.in)).readLine().equals("stop"));
        network.terminate();
    }
}
