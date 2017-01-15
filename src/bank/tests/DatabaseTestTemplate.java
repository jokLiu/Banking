package bank.tests;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bank.server.database.Connect;
import bank.server.database.CreateDBTables;
import bank.server.database.ManageDatabase;
import bank.utilities.CardType;
import bank.utilities.CustomerInformation;
import bank.utilities.Titles;

public abstract class DatabaseTestTemplate {

	protected ManageDatabase db;
	private Connection connection = null;

	public DatabaseTestTemplate() {
		super();
	}

	@Before
	public void setUp() throws SQLException {
		Connect conn = new Connect(connection);
		connection = conn.getConnection();
		
		CreateDBTables create = new CreateDBTables(connection);
		create.deleteTables();
		create.createTables();
		
		db= new ManageDatabase(connection);
		CustomerInformation info = createSetData();
		db.addAccount(info);
		
	}

	protected CustomerInformation createSetData() {
		CustomerInformation info = new CustomerInformation();
		
		info.setTitle(Titles.Dr);
		info.setFirstName("Jokubas");
		info.setSurname("Liutkus");
		info.setStreet("Pershore Road");
		info.setStreetNr("1165A");
		info.setCity("Birmingham");
		info.setPostCode("B30 2YN");
		info.setEmail("liutkusjok@gmail.com");
		info.setMainTel("+447548001657");
		info.setTelNr2("+447548001658");
		info.setTelNr3("+447548001659");
		info.setUsername("jokubas");
		info.setPassword("hdnjas1524A");
		info.setSecretWord("advn15345");
		info.setType(CardType.MasterCard);
		
		return info;
		
	}

	@After
	public void after() throws SQLException {
		connection.close();
	}
	
	@Test
	public abstract void test() throws Exception;

}