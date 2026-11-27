package dividedbroker;

import alarmevent.PoliceDepartment;
import eventbroker.EventBroker;
import network.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class ClientPoliceDep {

    public static void main(String[] args) throws IOException {

        EventBroker broker = EventBroker.getEventBroker();
        broker.start();

        PoliceDepartment policeDepartment = new PoliceDepartment();

        Network network = new Network();
        network.connect(InetAddress.getLocalHost(), 1024);

        while (!new BufferedReader(new InputStreamReader(System.in)).readLine().equals("stop"));
        network.terminate();
    }
}
