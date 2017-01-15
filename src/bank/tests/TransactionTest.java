package bank.tests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.Random;

import bank.utilities.Transaction;

public class TransactionTest {

	private Transaction trans;

	@Before
	public void setUp() throws Exception {
		trans = new Transaction();
	}

	@Test
	public void test() {
		idTest();
		descriptionTest();
		timeStampTest();
		amountTest();
	}
	
	private void idTest()
	{
		for(int i =1; i<10000; i++)
		{
			trans.setCustID(i);
			trans.setToID(i);
			assertEquals(trans.getCustID(), i);
			assertEquals(trans.getToID(), i);
		}
	}
	
	private void descriptionTest()
	{
		String s;
		for(int i=0; i<10000;i++)
		{
			//generating random string
			//code taken from security course
			byte[] array = new byte[20]; // length is bounded by 20
		    new Random().nextBytes(array);
		    String generatedString = new String(array, Charset.forName("UTF-8"));
		 
		    trans.setDesc(generatedString);
		    assertEquals(trans.getDesc(), generatedString);
		}
	}
	
	private void timeStampTest()
	{
		for(int i=0; i<10000; i++)
		{
			java.util.Date today = new java.util.Date();
			java.sql.Date date = new java.sql.Date(today.getTime());
			trans.setTimeStamp(date);
			assertEquals(trans.getTimeStamp(), date);
		}
	}
	
	private void amountTest()
	{
		double temp;
		for(int i=0; i<10000; i++)
		{
			temp = new Random().nextDouble();
			trans.setAmount(temp);
			assertEquals(trans.getAmount(), temp, 0.0000001);
		}
	}

}
