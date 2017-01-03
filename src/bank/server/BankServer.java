package bank.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

import bank.server.database.Connect;
import bank.server.database.CreateDBTables;
import bank.server.database.ManageDatabase;
import bank.utilities.CurrentCustomerTable;
import bank.utilities.Port;
public class BankServer {

	public static void main(String[] args) {

		// open the server socket
		ServerSocket serverSocket = null;

		// try opening the socket (exit if fails)
		try {
			serverSocket = new ServerSocket(Port.number);
		} catch (IOException e) {
			System.err.println("Failed to connect to the socket");
			System.exit(1);
		}
		
		
		//connecting to database
		Connection conn =null;	
		Connect connection = new Connect(conn);
		conn = connection.getConnection();
		
		//creating tables for the database
		CreateDBTables createTables = new CreateDBTables(conn);
//		createTables.deleteTables(); //deleting old tables
//		createTables.createTables(); //creating new tables
		
		//Creating for managing database queries 
		ManageDatabase database = new ManageDatabase(conn);
		
		
		//creating table for online customers
		CurrentCustomerTable customers = new CurrentCustomerTable();
		try {
			while (true) {

				// listen to connections
				Socket socket = serverSocket.accept();

				// establish streams from a single client
				ObjectInputStream readFromClient = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream writeToClient = new ObjectOutputStream(socket.getOutputStream());
				
				//Staring a thread to take care of a single customer connection
				(new ServerHelper(database, readFromClient, writeToClient, customers)).start();
			}
			
		} catch (IOException e) {
			System.err.println("error");
			System.exit(1);
		}

	}

}
