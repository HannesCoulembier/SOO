package network;

import eventbroker.Event;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.time.zone.ZoneOffsetTransition;

public class Connection {
    private Thread receiverThread = null;
    private volatile boolean receiverThreadRunning;

    private Socket s;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private Network network;

    public Connection(Socket socket, Network netw) throws IOException {
        s = socket;
        socketOut = new ObjectOutputStream(s.getOutputStream());
        socketOut.flush();
        socketIn = new ObjectInputStream(s.getInputStream());
        isClosed = false;
        network = netw;
    }
    public synchronized void send(Event e) throws IOException {
        socketOut.writeObject(e);
        socketOut.flush();
    }
    public void receive() {
        if (receiverThread == null) {
            receiverThread = new Thread(() -> {
                while (receiverThreadRunning) {
                    try {
                        Event e = (Event) socketIn.readObject();
                        network.receiveConnectionEvent(e, this);
                    } catch (EOFException | SocketException e) {
                        this.close();
                    } catch (IOException | ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            receiverThreadRunning = true;
            receiverThread.start();
        }
    }
    public boolean isClosed = true;
    public void close() {
        if (receiverThread != null)
            receiverThreadRunning = false;
        try {
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        isClosed = true;
    }
}
