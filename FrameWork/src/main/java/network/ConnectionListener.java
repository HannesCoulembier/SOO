package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// listens for incoming connections
public class ConnectionListener extends Thread {
    boolean running = true;

    Network network;
    ServerSocket serverSocket;
    public ConnectionListener(Network netw, int serverPort) throws IOException {
        network = netw;
        serverSocket = new ServerSocket(serverPort);
    }

    @Override
    public void run() {
        while (running) {
            try {
                Socket socket = serverSocket.accept();
                if (running)
                    network.connect(socket);
                else
                    socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Close the serverSocket afterward
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void terminate() {
        running = false;

    }
}
