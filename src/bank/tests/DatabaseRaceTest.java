package bank.tests;

import static org.junit.Assert.*;

import org.junit.Test;

public class DatabaseRaceTest extends DatabaseTestTemplate {

	@Test
	public void test() throws Exception {
		Runnable r = () -> {

			for (int j = 0; j < 10; j++) {
				
				try {
					db.depositMoney(1, 1000);
					db.withdrawMoney(1, 1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

		int n = 100;
		Thread[] t = new Thread[n];
		for(int i=0; i<n;i++)
		{
			t[i] = new Thread(r);
			t[i].start();
		}
		for(int i=0; i<n;i++)
		{
			t[i].join();
		}
		
		assertEquals(0,db.getBalance(1), 0.000001);

	}

}
