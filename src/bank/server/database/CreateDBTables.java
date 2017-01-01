package bank.server.database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//class for deleting the old tables and creating the new (fresh) tables
public class CreateDBTables {
	private Connection conn;
	
	public CreateDBTables(Connection conn)
	{
		this.conn= conn;
		
	}

	/**
	 * Delete previous tables from the database (cleaning the database)
	 */
	public void deleteTables() {
		System.out.println("Deleting all the tables");

		try {
			PreparedStatement del = conn.prepareStatement("DROP SCHEMA public CASCADE;");
			del.execute();

			PreparedStatement setClean = conn.prepareStatement("CREATE SCHEMA public;");
			setClean.execute();

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Failed to delete tables ");
			System.exit(1);
		}
		System.out.println("Successfully cleaned the tables");
	}


	/**
	 * Creates new tables.
	 */
	public void createTables() {
		System.out.println("Creating new tables");
		try {
			
			PreparedStatement createCustomerTable = conn.prepareStatement(  "CREATE TABLE Customer("
																		  + "id 		INTEGER," 
																		  + "PRIMARY KEY (id) " 
																		  + ");");
			
			
			PreparedStatement createNameTable = conn.prepareStatement(  "CREATE TABLE Name("
																	  + "id 		INTEGER," 
																	  + "title 		TEXT 	NOT NULL," 
																	  + "firstName 	TEXT 		NOT NULL,"
																	  + "surname 	TEXT		NOT NULL," 
																	  + "FOREIGN KEY (id) REFERENCES Customer(id) " 
																	  + "ON DELETE CASCADE " 
																	  + "ON UPDATE CASCADE " 
																	  + ");");
			
			PreparedStatement createAddressTable = conn.prepareStatement(   "CREATE TABLE Address("
																		  + "id 		INTEGER," 
																		  + "street 	TEXT		NOT NULL," 
																		  + "nr		 	INTEGER		NOT NULL,"
																		  + "city		TEXT		NOT NULL," 
																		  + "postCode	TEXT		NOT NULL," 
																		  + "FOREIGN KEY (id) REFERENCES Customer(id) " 
																		  + "ON DELETE CASCADE " 
																		  + "ON UPDATE CASCADE" 
																		  + ");");
			
			PreparedStatement createContactsTable = conn.prepareStatement(  "CREATE TABLE Contacts("
																	  + "id 		INTEGER," 
																	  + "email 		TEXT	 	NOT NULL	UNIQUE," 
																	  + "mainTelNr 	TEXT 		NOT NULL,"
																	  + "telNr2 	TEXT," 
																	  + "telNr3 	TEXT," 
																	  + "FOREIGN KEY (id) REFERENCES Customer(id) " 
																	  + "ON DELETE CASCADE " 
																	  + "ON UPDATE CASCADE " 
																	  + ");");
			
			PreparedStatement createAccountTable = conn.prepareStatement(  "CREATE TABLE Account("
																			  + "id 		INTEGER," 
																			  + "username	TEXT		NOT NULL 	UNIQUE,"
																			  + "password	TEXT		NOT NULL,"
																			  + "secretWord TEXT, "
																			  + "type		TEXT		NOT NULL,"
																			  + "balance	NUMERIC 	NOT NULL CHECK(balance>=0),"
																			  + "PRIMARY KEY (id) " 
																			  + ");");	
			
			PreparedStatement createTransfersTable = conn.prepareStatement(  "CREATE TABLE Transfers("
																		  + "fromID			INTEGER,"
																		  + "toID			INTEGER,"	
																		  + "description 	TEXT, "	
																		  + "timeStamp		DATE	NOT NULL, "	
																		  + "amount			NUMERIC 	NOT NULL	CHECK(amount>0)," 
																		  +	"FOREIGN KEY (fromID) REFERENCES Account(id) " 
																		  + "ON DELETE RESTRICT " 
																		  + "ON UPDATE CASCADE, " 
																		  +	"FOREIGN KEY (toID) REFERENCES Account(id) " 
																		  + "ON DELETE RESTRICT " 
																		  + "ON UPDATE CASCADE " 
																		  + ");");	
			
			
			

			PreparedStatement createDepositTable = conn.prepareStatement(  "CREATE TABLE Deposit("
																		  + "fromID			INTEGER,"
																		  + "description 	TEXT, "	
																		  + "timeStamp		DATE	NOT NULL, "	
																		  + "amount			NUMERIC 	NOT NULL," 
																		  +	"FOREIGN KEY (fromID) REFERENCES Account(id) " 
																		  + "ON DELETE RESTRICT " 
																		  + "ON UPDATE CASCADE " 
																		  + ");");	
			
			
			
			PreparedStatement createCustAccTable = conn.prepareStatement("CREATE TABLE CustAcc(" 
																  + "custID		INTEGER," 
																  + "accID 		INTEGER,"
																  + "FOREIGN KEY (custID) REFERENCES Customer(id) " 
																  + "ON DELETE CASCADE " 
																  + "ON UPDATE CASCADE," 
																  +	"FOREIGN KEY (accID) REFERENCES Account(id) " 
																  + "ON DELETE CASCADE " 
																  + "ON UPDATE CASCADE " 
																  + ");");

			createCustomerTable.executeUpdate();
			createNameTable.executeUpdate();
			createAddressTable.executeUpdate();
			createContactsTable.executeUpdate();
			createAccountTable.executeUpdate();
			createTransfersTable.executeUpdate();
			createDepositTable.executeUpdate();
			createCustAccTable.executeUpdate();
			
		} catch (SQLException e) {
			System.err.println(e.getCause());
			System.err.println("Failed to create new tables!");
			System.exit(1);
		}
		System.out.println("Tables created");
	}
	
}
