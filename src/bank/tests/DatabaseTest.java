package bank.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import bank.utilities.CustomerInformation;

public class DatabaseTest extends DatabaseTestTemplate {

	@Test
	public void test() throws Exception {
		
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
