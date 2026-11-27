package network;

import eventbroker.Event;
import eventbroker.EventBroker;
import eventbroker.EventListener;
import eventbroker.EventPublisher;

import java.io.EOFException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Network extends EventPublisher implements EventListener {
    private ConnectionListener conListener = null;
    private final ArrayList<Connection> connections = new ArrayList<>();

    public Network() {
        System.out.println("Opening Network as a client");
        EventBroker e = EventBroker.getEventBroker();
        e.addEventListener(this);
    }
    public Network(int serverPort) throws IOException {
        System.out.println("Opening Network as a server");
        EventBroker e = EventBroker.getEventBroker();
        e.addEventListener(this);

        conListener = new ConnectionListener(this, serverPort);
        conListener.start();
    }

    public void connect(InetAddress address, int port) {
        Socket socket;
        try {
            socket = new Socket(address, port);
        } catch (IOException e) {
            System.out.println("Couldn't connect to "+address+" at port: "+port);
            return;
        }
        connect(socket);
    }

    public void connect(Socket socket) {
        Connection con;
        try {
            con = new Connection(socket, this);
        } catch (EOFException e) { // Socket closed other side right after accepting, causing EOF exception when getting the streams
            System.out.println("Failed to connect: Socket closed");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        con.receive();
        connections.add(con);
    }

    public void terminate() {
        if (conListener != null)
            conListener.terminate();
        for (Connection con : connections) {
            if (!con.isClosed) { con.close(); continue; }
        }
        System.out.println("Terminated Network");
    }

    public void receiveConnectionEvent(Event e, Connection c) {
        this.publishEvent(e);
        synchronized (connections) {
            connections.removeIf(cons -> cons.isClosed);
        }
        for (Connection cons : connections) {
            if (cons != c) {
                try {
                    cons.send(e);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    @Override
    public void handleEvent(Event e) {
        for (Connection con : connections) {
            if (con.isClosed) { connections.remove(con); continue; }
            try {
                con.send(e);
            } catch (IOException ex) {
                System.err.println("Couldn't send event due to the following error:");
                ex.printStackTrace();
                return;
            }
        }
    }
}
