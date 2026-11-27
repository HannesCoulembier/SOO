
package alarmevent;

import eventbroker.EventBroker;
import network.Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Server {

    public static void main(String[] args) throws IOException {

        EventBroker broker = EventBroker.getEventBroker();
        broker.start();

        FireDepartment fireDepartment = new FireDepartment();
        PoliceDepartment policeDepartment = new PoliceDepartment();
        Hospital hospital1 = new Hospital("UZ");
        Hospital hospital2 = new Hospital("AZ");

        Network network = new Network(1024);

        while (!new BufferedReader(new InputStreamReader(System.in)).readLine().equals("stop"));
        network.terminate();
    }
}
