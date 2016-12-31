import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;

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
		createTables.deleteTables();
		createTables.createTables();
//
		
//		char[] lol = new char[3];
//		lol[1]='a';
//		lol[0]='l';
//		lol[2]='b';
//		
//		System.out.println(lol.toString());
		
	/*	//testing 
		
		CustomerInformation info = new CustomerInformation();
		info.setTitle(Titles.Mr);
		info.setFirstName("Jok");
		info.setSurname("liu");
		info.setStreet("Pershore Road");
		info.setStreetNr(1167);
		info.setCity("Birmingham");
		info.setPostCode("B302YN");
		info.setEmail("jxl706@gmail.com");
		info.setMainTel("07548001657");
		info.setTelNr2("07548001657");
		info.setTelNr3("07548001657");
		info.setUsername("jxl706");
		info.setPassword("123456");
		info.setSecretWord("akvile");
		info.setType(CardType.Credit);
		*/
		//initialising the object to deal with different requests
		ManageDatabase database = new ManageDatabase(conn);
		
		/*
		database.addAccount(info);
		System.out.println(database.getBalance(1000));
		database.depositMoney(1000, 123.15);
		System.out.println(database.getBalance(1000));*/
		// loop forever listening to new connections
		try {
			while (true) {

				// listen to connections
				Socket socket = serverSocket.accept();

				// establish streams from a single client
				ObjectInputStream readFromClient = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream writeToClient = new ObjectOutputStream(socket.getOutputStream());
				System.out.println("accepted");
			
				(new ServerHelper(database, readFromClient, writeToClient)).start();
			}
			
		} catch (IOException e) {
			System.err.println("error");
			System.exit(1);
		}

	}

}
