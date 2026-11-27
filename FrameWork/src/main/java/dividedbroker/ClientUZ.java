package dividedbroker;

import alarmevent.Hospital;
import eventbroker.EventBroker;
import network.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

public class ClientUZ {

    public static void main(String[] args) throws IOException {

        EventBroker broker = EventBroker.getEventBroker();
        broker.start();

        Hospital hospital = new Hospital("UZ");

        Network network = new Network();
        network.connect(InetAddress.getLocalHost(), 1024);

        while (!new BufferedReader(new InputStreamReader(System.in)).readLine().equals("stop"));
        network.terminate();
    }
}
