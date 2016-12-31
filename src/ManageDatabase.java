import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Date;

public class ManageDatabase {
	
	private Connection conn;
	private int id = 1000;
	public ManageDatabase(Connection conn)
	{
		this.conn = conn;
	}
	
	
	public synchronized void addAccount(CustomerInformation info)
	{
		try {
//			TODO fix the id problem
			//creating customer
			PreparedStatement singleCustomer = conn
					.prepareStatement("INSERT INTO Customer (id) " + "VALUES (?); ");
			singleCustomer.setInt(1, id);
			singleCustomer.executeUpdate();
			
			//creating name
			PreparedStatement singleName = conn
					.prepareStatement("INSERT INTO Name (id, title, firstName, surname) " + "VALUES (? , ? , ? , ?); ");
			singleName.setInt(1, id);
			singleName.setString(2, info.getTitle().toString());
			singleName.setString(3, info.getFirstName());
			singleName.setString(4,	info.getSurname());
			singleName.executeUpdate();
		
			//creating address
			PreparedStatement singleAddress = conn
					.prepareStatement("INSERT INTO Address (id,street, nr, city, postCode) " + "VALUES (? , ? , ? , ?, ?); ");
			singleAddress.setInt(1, id);
			singleAddress.setString(2, info.getStreet());
			singleAddress.setInt(3, info.getStreetNr());
			singleAddress.setString(4, info.getCity());
			singleAddress.setString(5, info.getPostCode());
			singleAddress.executeUpdate();
			
			//creating contacts
			PreparedStatement singleContacts = conn
					.prepareStatement("INSERT INTO Contacts (id,email, mainTelNr, telNr2, telNr3) " + "VALUES (? , ? , ? , ?, ?); ");
			singleContacts.setInt(1, id);
			singleContacts.setString(2, info.getEmail());
			singleContacts.setString(3, info.getMainTel());
			if (info.getTelNr2() != null) singleContacts.setString(4, info.getTelNr2()) ;
			if (info.getTelNr3() != null) singleContacts.setString(5, info.getTelNr3()) ;
			singleContacts.executeUpdate();
			
			//creating an account for customer
			PreparedStatement singleAcc = conn
					.prepareStatement("INSERT INTO Account (id,username, password, secretWord, type,balance) " + "VALUES (? , ? , ? , ?, ?, ?); ");
			singleAcc.setInt(1, id);
			singleAcc.setString(2, info.getUsername());
			singleAcc.setString(3, info.getPassword());
			singleAcc.setString(4, info.getSecretWord());
			singleAcc.setString(5, info.getType().toString());
			singleAcc.setInt(6, 0);
			singleAcc.executeUpdate();
			
			//creating relationship
			PreparedStatement singleAccount = conn
					.prepareStatement("INSERT INTO CustAcc (custID,accID) " + "VALUES (? , ? ); ");
			singleAccount.setInt(1, id);
			singleAccount.setInt(2, id);
			singleAccount.executeUpdate();
			
			
			id++;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
				
	}
	
	public synchronized void depositMoney(int id , double amount)
	{
		try {
			PreparedStatement singleCustomer = conn
					.prepareStatement("BEGIN;"
							+ "UPDATE Account SET balance = balance + ? "
							+ "WHERE id = ?; "
							+ "INSERT INTO Deposit (fromID, description, timeStamp, amount) VALUES (?, ?, ?, ?) ;"
							+ " COMMIT;");
			singleCustomer.setDouble(1, amount);
			singleCustomer.setInt(2, id);
			singleCustomer.setInt(3, id);
			singleCustomer.setString(4, "Deposit");
			 java.util.Date today = new java.util.Date();
			singleCustomer.setDate(5, new java.sql.Date(today.getTime()));
			singleCustomer.setDouble(6, amount);
			singleCustomer.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public synchronized void withdrawMoney(int id , double amount)
	{
		try {
			PreparedStatement singleCustomer = conn
					.prepareStatement("BEGIN;"
							+ "UPDATE Account SET balance = balance - ? "
							+ "WHERE id = ?; "
							+ "INSERT INTO Deposit (fromID, description, timeStamp, amount) VALUES (?, ?, ?, ?) ;"
							+ " COMMIT;");
			singleCustomer.setDouble(1, amount);
			singleCustomer.setInt(2, id);
			singleCustomer.setInt(3, id);
			singleCustomer.setString(4, "Withdrawal");
			 java.util.Date today = new java.util.Date();
			singleCustomer.setDate(5,  new java.sql.Date(today.getTime()));
			singleCustomer.setDouble(6, amount);
			singleCustomer.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public synchronized void transfer(int id , int toId,  double amount)
	{
		try {
			PreparedStatement singleCustomer = conn
					.prepareStatement("BEGIN;"
							+ "UPDATE Account SET balance = balance - ? "
							+ "WHERE id = ?; "
							+ "UPDATE Account SET balance = balance + ? "
							+ "WHERE id = ?; "
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
			singleCustomer.setDate(8,  new java.sql.Date(today.getTime()));
			singleCustomer.setDouble(9, amount);
			singleCustomer.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized CustomerInformation getInformation(int id)
	{
		CustomerInformation info = new CustomerInformation();
		
		try {
			PreparedStatement getName = conn
					.prepareStatement( "SELECT * FROM Name WHERE id = ? ;"
							);
			PreparedStatement getAddress = conn
					.prepareStatement( "SELECT * FROM Address WHERE id = ? ;"
							);
			PreparedStatement getContacts = conn
					.prepareStatement( "SELECT * FROM Contacts WHERE id = ? ;"
							);
			

			getName.setInt(1, id);
			getAddress.setInt(1, id);
			getContacts.setInt(1, id);
			ResultSet nameRes = getName.executeQuery();
			ResultSet addressRes  = getAddress.executeQuery();
			ResultSet contRes  = getContacts.executeQuery();
			
			
			while(nameRes.next())
			{
				info.setTitle(Titles.valueOf(nameRes.getString("title")));
				info.setFirstName(nameRes.getString("firstName"));
				info.setSurname(nameRes.getString("surname"));
			}
			while(addressRes.next())
			{
				info.setStreet(addressRes.getString("street"));
				info.setStreetNr(addressRes.getInt("nr"));
				info.setCity(addressRes.getString("city"));
				info.setPostCode(addressRes.getString("postCode"));
			}
			while(contRes.next())
			{
				info.setEmail(contRes.getString("email"));
				info.setMainTel(contRes.getString("mainTelNr"));
				info.setTelNr2(contRes.getString("telNr2"));
				info.setTelNr3(contRes.getString("telNr3"));
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return info;
	}
	
	
	public double getBalance(int id)
	{
		try {
			PreparedStatement getBalance = conn
					.prepareStatement( "SELECT balance FROM Account WHERE id = ? ;"
							);
			
			getBalance.setInt(1, id);
			
			ResultSet balanceRes = getBalance.executeQuery();
			
			while(balanceRes.next())
			{
				return balanceRes.getInt("balance");
			}
		} catch (SQLException e) {
						e.printStackTrace();
		}
		
		return 0;
	}
	
	
	//return secret word if valid, otherwise null
	public String isUserValid(String username, String psw)
	{
		try {
			PreparedStatement getAccount = conn
					.prepareStatement( "SELECT secretWord FROM Account WHERE username = ? AND password = ? ;"
							);
			
			getAccount.setString(1, username);
			getAccount.setString(2, psw);
			
			ResultSet res = getAccount.executeQuery();
			
			while(res.next())
			{
				return res.getString("secretWord");
			}
		} catch (SQLException e) {
			return null;
		}
		return null;
	}
	
	
	public int getId(String usr, String psw)
	{
		try {
			PreparedStatement getAccount = conn
					.prepareStatement( "SELECT id FROM Account WHERE username = ? AND password = ? ;"
							);
			
			getAccount.setString(1, usr);
			getAccount.setString(2, psw);
			
			ResultSet res = getAccount.executeQuery();
			
			while(res.next())
			{
				return res.getInt("id");
			}
		} catch (SQLException e) {
			return 0;
		}
		return 0;
	}
}
