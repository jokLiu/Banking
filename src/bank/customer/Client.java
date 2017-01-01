package bank.customer;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JFrame;

import bank.utilities.Port;
import bank.views.LogInView;

//main class for Customer
public class Client {
	public static void main(String[] args) {

		// Check correct usage:
		if (args.length != 1) {
			System.err.println("Usage: java Client hostname");
			System.exit(1); // Give up.
		}
		String hostname = args[0];

		// creating and initialising streams and socket
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
		
		
		ObjectOutputStream toServer2 = toServer;
		ObjectInputStream fromServer2 = fromServer;
		EventQueue.invokeLater(() -> {
			JFrame frame = new LogInView(toServer2, fromServer2);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});

		
		Thread helper = new CustomerHelper(fromServer, toServer);
		helper.start();

		//wait for thread to finish
		try {
			helper.join();
		} catch (InterruptedException e1) {

		}

		// closing the streams and the socket with the server
		try {
			toServer.close();
			fromServer.close();
			socket.close();
		} catch (IOException e) {

		}

	}
}
