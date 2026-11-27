package serialization;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        InetAddress host = null;
        int serverPort = 1024;
        Socket socket = null;
        BufferedReader consoleInput;
        String firstName;
        try {
            host = InetAddress.getLocalHost();
            socket = new Socket(host, serverPort);
            ObjectOutputStream socketOut = new ObjectOutputStream(socket.getOutputStream());
            socketOut.flush();
            ObjectInputStream socketIn = new ObjectInputStream(socket.getInputStream());
            do {
                // read in a name
                consoleInput = new BufferedReader(new InputStreamReader(System.in));
                System.out.println("Enter First Name : ");
                firstName = consoleInput.readLine();
                System.out.println("Enter Last Name : ");
                String lastName = consoleInput.readLine();

                if (!(firstName.equals("stop"))) {
                    // send the name
                    Person p = new Person(firstName, lastName);
                    socketOut.writeObject(p);

                    // receive reply
                    Person reply = (Person) socketIn.readObject();

                    // print the greeting
                    System.out.println("Reply from server = " + reply);
                }
            } while (!(firstName.equals("stop")));
        } catch (IOException | ClassNotFoundException e) {
            System.err.println(e);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
