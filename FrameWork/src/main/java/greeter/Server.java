package greeter;
import java.net.*;
import java.io.*;

public class Server {
	 public static void main(String[] args) {
		 ServerSocket serverSocket = null;
		 int serverPort = 1024;
		 try {
			 serverSocket = new ServerSocket(serverPort);
			 Socket socket = null;
			 while (true) {
				 socket = serverSocket.accept();
				 PrintWriter socketOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
				 BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				 while (true) {
					 String request = socketIn.readLine();
					 String rMess = "Hello, " + request;
					 socketOut.println(rMess);
				 }
				 // socket.close();
			 }
		 }
	 	catch(SocketException e){
			 System.err.println(e);
		 } catch(IOException e){
			 System.err.println(e);
		 } finally{
			 if (serverSocket != null) {
				 try {
					 serverSocket.close();
				 } catch (IOException e) {
					 throw new RuntimeException(e);
				 }
			 }
		 }
	 }
}
