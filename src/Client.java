import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	public static void main(String[] args) {
		// Check correct usage:
		if (args.length != 1) {
			System.err.println("Usage: java Client hostname");
			System.exit(1); // Give up.
		}
		String hostname = args[0];

		//creating and initialising streams and socket
		ObjectInputStream fromServer = null;
		ObjectOutputStream toServer = null;
		Socket socket = null;

		try {
			socket = new Socket(hostname, Port.number);
			toServer = new ObjectOutputStream(socket.getOutputStream());
			fromServer = new ObjectInputStream(socket.getInputStream());

		} catch (UnknownHostException e) {
			System.err.println("Unknown host: " + hostname);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Server is not running");
			System.exit(1);
		}
		
		Thread helper = new CustomerHelper(fromServer, toServer);
		helper.start();
		
		try {
			helper.join();
		} catch (InterruptedException e1) {
			
		}

		//closing the streams and the socket with the server
		try {
			toServer.close();
			fromServer.close();
			socket.close();
		} catch (IOException e) {

		}

	}
}
