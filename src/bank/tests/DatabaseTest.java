package bank.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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

public class DatabaseTest {

	private ManageDatabase db;
	private Connection connection = null;
	@Before
	public void setUp() {
		Connect conn = new Connect(connection);
		connection = conn.getConnection();
		
		CreateDBTables create = new CreateDBTables(connection);
		create.deleteTables();
		create.createTables();
		
		db= new ManageDatabase(connection);
		
	}
	
	
	private CustomerInformation createSetData()
	{
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
	public void after() throws SQLException
	{
		connection.close();
	}

	
	@Test
	public void test() throws Exception {
		CustomerInformation info = createSetData();
		db.addAccount(info);
		db.depositMoney(1, 1000.50);
		assertEquals(db.getBalance(1),1000.50, 0.000001);
		db.withdrawMoney(1, 250.50);
		assertNotEquals(db.getBalance(1),1000.50);
		assertEquals(db.getBalance(1),750, 0.0000001);
		assertEquals(db.isUserValid("jokubas","hdnjas1524A"), ("advn15345"));
		assertEquals(db.getId("jokubas","hdnjas1524A"),1);
		assertEquals(db.isUserValid(1), true);
		assertEquals(db.isUserValid(2), false);
		assertEquals(db.getDepositsAndWithdrawals(1).get(1).getAmount(), 250.50, 0.000001);
		
		
		
	}

}
