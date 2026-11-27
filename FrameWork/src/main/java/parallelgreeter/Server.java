package parallelgreeter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

public class Server {

    private static void handleSocket(Socket socket) {
        try {
            PrintWriter socketOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            while (true) {
                String request = socketIn.readLine();
                String rMess = "Hello, " + request;
                socketOut.println(rMess);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args){
        ServerSocket serverSocket = null;
        int serverPort = 1024;
        LinkedList<Thread> sockets = new LinkedList<>();

        try {
            serverSocket = new ServerSocket(serverPort);
            while (true) {
                Socket socket = serverSocket.accept();
                Thread t = new Thread(()->{handleSocket(socket);});
                t.start();
                sockets.add(t);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
}

