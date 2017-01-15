package bank.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bank.utilities.LogIn;

public class LogInInfoTest {

	private LogIn details;

	@Before
	public void setUp() throws Exception {
		details = new LogIn();
	}

	@Test
	public void test() {
		String name = "fasdasdasga";
		String psw = "fjalkdsf465asdf4as6";
		for (char i = 'a'; i <= 'z'; i++) {
			name += i;
			psw +=i;
			details.setUsr(name);
			details.setPsw(psw);
			
			assertEquals(details.getUsr(), name);
			assertEquals(details.getPsw(), psw);
		}
	}

}
