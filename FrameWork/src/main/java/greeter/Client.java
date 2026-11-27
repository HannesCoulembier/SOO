package greeter;
import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) {
		InetAddress host = null;
		int serverPort = 1024;
		Socket socket = null;
		BufferedReader consoleInput;
		String name = "";
		try {
			host = InetAddress.getLocalHost();
			socket = new Socket(host, serverPort);
			PrintWriter socketOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			do {
				// read in a name
				System.out.println("Enter a name : ");
				consoleInput = new BufferedReader(new InputStreamReader(System.in));
				name = consoleInput.readLine();

				if (!(name.equals("stop"))) {
					// send the name
					socketOut.println(name);

					// receive reply
					String reply = socketIn.readLine();

					// print the greeting
					System.out.println("Reply from server = " + reply);
				}
			} while (!(name.equals("stop")));
		} catch (IOException e) {
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

