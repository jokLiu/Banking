package bank.server.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bank.utilities.CustomerInformation;
import bank.utilities.Titles;
import bank.utilities.Transaction;

//class for managing the database (dealing with requests)
public class ManageDatabase {

	private Connection conn;

	public ManageDatabase(Connection conn) {
		this.conn = conn;
	}

	// Adding the account
	public synchronized void addAccount(CustomerInformation info) throws SQLException {
		try {
			int id = getNextId();
			// creating customer
			PreparedStatement singleCustomer = conn
					.prepareStatement("BEGIN; INSERT INTO Customer (id) " + "VALUES (?); ");
			singleCustomer.setInt(1, id);
			singleCustomer.executeUpdate();

			// creating name
			PreparedStatement singleName = conn
					.prepareStatement("INSERT INTO Name (id, title, firstName, surname) " + "VALUES (? , ? , ? , ?); ");
			singleName.setInt(1, id);
			singleName.setString(2, info.getTitle().toString());
			singleName.setString(3, info.getFirstName());
			singleName.setString(4, info.getSurname());
			singleName.executeUpdate();

			// creating address
			PreparedStatement singleAddress = conn.prepareStatement(
					"INSERT INTO Address (id,street, nr, city, postCode) " + "VALUES (? , ? , ? , ?, ?); ");
			singleAddress.setInt(1, id);
			singleAddress.setString(2, info.getStreet());
			singleAddress.setString(3, info.getStreetNr());
			singleAddress.setString(4, info.getCity());
			singleAddress.setString(5, info.getPostCode());
			singleAddress.executeUpdate();

			// creating contacts
			PreparedStatement singleContacts = conn.prepareStatement(
					"INSERT INTO Contacts (id,email, mainTelNr, telNr2, telNr3) " + "VALUES (? , ? , ? , ?, ?); ");
			singleContacts.setInt(1, id);
			singleContacts.setString(2, info.getEmail());
			singleContacts.setString(3, info.getMainTel());
			if (info.getTelNr2() != null)
				singleContacts.setString(4, info.getTelNr2());
			if (info.getTelNr3() != null)
				singleContacts.setString(5, info.getTelNr3());
			singleContacts.executeUpdate();

			// creating an account for customer
			PreparedStatement singleAcc = conn
					.prepareStatement("INSERT INTO Account (id,username, password, secretWord, type,balance) "
							+ "VALUES (? , ? , ? , ?, ?, ?); ");
			singleAcc.setInt(1, id);
			singleAcc.setString(2, info.getUsername());
			singleAcc.setString(3, info.getPassword());
			singleAcc.setString(4, info.getSecretWord());
			singleAcc.setString(5, info.getType().toString());
			singleAcc.setInt(6, 0);
			singleAcc.executeUpdate();

			// creating relationship
			PreparedStatement singleAccount = conn
					.prepareStatement("INSERT INTO CustAcc (custID,accID) " + "VALUES (? , ? ); COMMIT;");
			singleAccount.setInt(1, id);
			singleAccount.setInt(2, id);
			singleAccount.executeUpdate();
		} catch (SQLException e) {
			
			PreparedStatement rollback = conn
					.prepareStatement("ROLLBACK;");
			rollback.executeUpdate();
			throw new SQLException();
		}

	}

	// getting the next unique id
	private int getNextId() {
		int l = 1000;
		PreparedStatement nextIdQuery;
		try {
			// executing query
			nextIdQuery = conn.prepareStatement("SELECT MAX(id) AS id FROM Account;");
			ResultSet res = nextIdQuery.executeQuery();

			while (res.next()) {
				l = res.getInt("id");
			}
			System.out.println(l);
		} catch (SQLException e) {
		}
		return l + 1;
	}

	// query for depositing money to an account
	public synchronized void depositMoney(int id, double amount) throws SQLException {

		PreparedStatement singleCustomer = conn.prepareStatement("BEGIN;" + "UPDATE Account SET balance = balance + ? "
				+ "WHERE id = ?; "
				+ "INSERT INTO Deposit (fromID, description, timeStamp, amount) VALUES (?, ?, ?, ?) ;" + " COMMIT;");
		singleCustomer.setString(4, "Deposit");
		executeStatement(singleCustomer, id, amount);

	}

	// query for withdrawing money from an account
	public synchronized void withdrawMoney(int id, double amount) throws SQLException {
		PreparedStatement singleCustomer = conn.prepareStatement("BEGIN;" + "UPDATE Account SET balance = balance - ? "
				+ "WHERE id = ?; "
				+ "INSERT INTO Deposit (fromID, description, timeStamp, amount) VALUES (?, ?, ?, ?) ;" + " COMMIT;");
		singleCustomer.setString(4, "Withdrawal");
		executeStatement(singleCustomer, id, amount);
	}

	// method for dealing with deposit and withdraw methods
	// refactored to avoid duplications
	private synchronized void executeStatement(PreparedStatement singleCustomer, int id, double amount)
			throws SQLException {
		singleCustomer.setDouble(1, amount);
		singleCustomer.setInt(2, id);
		singleCustomer.setInt(3, id);
		java.util.Date today = new java.util.Date();
		singleCustomer.setDate(5, new java.sql.Date(today.getTime()));
		singleCustomer.setDouble(6, amount);
		singleCustomer.executeUpdate();
	}

	// transfer method for dealing with money transfers from one account to
	// another
	public synchronized void transfer(int id, int toId, double amount) throws SQLException {

		PreparedStatement singleCustomer = conn.prepareStatement("BEGIN;" + "UPDATE Account SET balance = balance - ? "
				+ "WHERE id = ?; " + "UPDATE Account SET balance = balance + ? " + "WHERE id = ?; "
				+ "INSERT INTO Transfers (fromID, toID, description, timeStamp, amount) VALUES (? , ?, ?, ?, ?) ;"
				+ " COMMIT;");
		singleCustomer.setDouble(1, amount);
		singleCustomer.setInt(2, id);
		singleCustomer.setDouble(3, amount);
		singleCustomer.setInt(4, toId);
		singleCustomer.setInt(5, id);
		singleCustomer.setInt(6, toId);
		singleCustomer.setString(7, "Transfer");

		java.util.Date today = new java.util.Date();
		singleCustomer.setDate(8, new java.sql.Date(today.getTime()));
		singleCustomer.setDouble(9, amount);
		singleCustomer.executeUpdate();

	}

	// getting all the information about the customer
	// apart from secret log in details
	public synchronized CustomerInformation getInformation(int id) throws SQLException {
		CustomerInformation info = new CustomerInformation();

		PreparedStatement getName = conn.prepareStatement("SELECT * FROM Name WHERE id = ? ;");
		PreparedStatement getAddress = conn.prepareStatement("SELECT * FROM Address WHERE id = ? ;");
		PreparedStatement getContacts = conn.prepareStatement("SELECT * FROM Contacts WHERE id = ? ;");

		getName.setInt(1, id);
		getAddress.setInt(1, id);
		getContacts.setInt(1, id);
		ResultSet nameRes = getName.executeQuery();
		ResultSet addressRes = getAddress.executeQuery();
		ResultSet contRes = getContacts.executeQuery();

		while (nameRes.next()) {
			info.setTitle(Titles.valueOf(nameRes.getString("title")));
			info.setFirstName(nameRes.getString("firstName"));
			info.setSurname(nameRes.getString("surname"));
		}
		while (addressRes.next()) {
			info.setStreet(addressRes.getString("street"));
			info.setStreetNr(addressRes.getString("nr"));
			info.setCity(addressRes.getString("city"));
			info.setPostCode(addressRes.getString("postCode"));
		}
		while (contRes.next()) {
			info.setEmail(contRes.getString("email"));
			info.setMainTel(contRes.getString("mainTelNr"));
			info.setTelNr2(contRes.getString("telNr2"));
			info.setTelNr3(contRes.getString("telNr3"));
		}

		return info;
	}

	// getting the balance of an account
	public double getBalance(int id) throws SQLException {

		PreparedStatement getBalance = conn.prepareStatement("SELECT balance FROM Account WHERE id = ? ;");

		getBalance.setInt(1, id);

		ResultSet balanceRes = getBalance.executeQuery();

		while (balanceRes.next()) {
			return balanceRes.getDouble("balance");
		}

		return 0;
	}

	// return secret word if valid, otherwise null
	// this determines if the username and password was correct
	public String isUserValid(String username, String psw) throws SQLException {

		PreparedStatement getAccount = conn
				.prepareStatement("SELECT secretWord FROM Account WHERE username = ? AND password = ? ;");

		getAccount.setString(1, username);
		getAccount.setString(2, psw);

		ResultSet res = getAccount.executeQuery();

		while (res.next()) {
			return res.getString("secretWord");
		}
		return null;
	}

	// getting the account id by the username and password
	public int getId(String usr, String psw) throws SQLException {

		PreparedStatement getAccount = conn
				.prepareStatement("SELECT id FROM Account WHERE username = ? AND password = ? ;");

		getAccount.setString(1, usr);
		getAccount.setString(2, psw);

		ResultSet res = getAccount.executeQuery();

		while (res.next()) {
			return res.getInt("id");
		}
		return 0;
	}

	// return if a user exists or not
	public boolean isUserValid(int id) throws SQLException {
		PreparedStatement getAccount = conn.prepareStatement("SELECT id FROM Account WHERE id = ?");

		getAccount.setInt(1, id);

		ResultSet res = getAccount.executeQuery();

		while (res.next()) {
			return true;
		}
		return false;
	}
	
	//returns the deposit of all the deposits and withrawals
	public ArrayList<Transaction> getDepositsAndWithdrawals(int id) throws SQLException
	{
		
		ArrayList<Transaction> info = new ArrayList<>();
		
		PreparedStatement getTransactions = conn.prepareStatement("SELECT fromid, description, timestamp, amount  FROM Deposit WHERE fromid = ?");
		getTransactions.setInt(1, id);
		ResultSet transRes = getTransactions.executeQuery();
		
		while(transRes.next())
		{
			Transaction temp = new Transaction();
			temp.setCustID(transRes.getInt("fromid"));
			temp.setDesc(transRes.getString("description"));
			temp.setTimeStamp(transRes.getDate("timestamp"));
			temp.setAmount(transRes.getDouble("amount"));
			info.add(temp);
		}
		
	
		return info;
	
	}
	
	//returns all the transfers
	public ArrayList<Transaction> getTransfers(int id ) throws SQLException
	{
		ArrayList<Transaction> info = new ArrayList<>();
		
		PreparedStatement getTransfers = conn.prepareStatement("SELECT fromid, toid, description, timestamp, amount  FROM Transfers WHERE fromid = ? OR toid = ?");
		getTransfers.setInt(1, id);
		getTransfers.setInt(2, id);
		ResultSet transfersRes = getTransfers.executeQuery();
		
		while(transfersRes.next())
		{
			Transaction temp = new Transaction();
			temp.setCustID(transfersRes.getInt("fromid"));
			temp.setToID(transfersRes.getInt("toid"));
			temp.setDesc(transfersRes.getString("description"));
			temp.setTimeStamp(transfersRes.getDate("timestamp"));
			temp.setAmount(transfersRes.getDouble("amount"));
			info.add(temp);
		}
		return info;
	}
}
