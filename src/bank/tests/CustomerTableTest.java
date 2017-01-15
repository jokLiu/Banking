package bank.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bank.utilities.CurrentCustomerTable;

public class CustomerTableTest {
	private CurrentCustomerTable table;

	@Before
	public void setUp() throws Exception {
		table = new CurrentCustomerTable();

	}

	@Test
	public void test() {
		int n = 100;
		for (int i = 1; i <= n; i++) {
			table.addCustomer(i, null);
			assertTrue(table.exists(i));
		}

		for (int i = 1; i <= n; i++) {
			assertTrue(table.exists(i));
			table.deleteCustomer(i);
			assertFalse(table.exists(1));
		}
	}

}
