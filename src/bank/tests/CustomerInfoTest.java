package bank.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import bank.utilities.CardType;
import bank.utilities.CustomerInformation;
import bank.utilities.Titles;

public class CustomerInfoTest {

	private CustomerInformation info;

	@Before
	public void setUp() throws Exception {
		info = new CustomerInformation();
	}

	@Test
	public void test() {

		titleTest();
		fullNameTest();
		addressTest();
		contactsTest();
		userDetailsTest();
		cardTypeTest();

	}

	private void titleTest() {
		// Title test
		info.setTitle(Titles.Dr);
		assertEquals(info.getTitle(), Titles.Dr);
		info.setTitle(Titles.Miss);
		assertNotEquals(info.getTitle(), Titles.Dr);
		assertEquals(info.getTitle(), Titles.Miss);
	}

	private void fullNameTest() {
		String name = "Jokubas";
		String surname = "Liutkus";
		info.setFirstName(name);
		info.setSurname(surname);
		assertEquals(info.getFirstName(), name);
		assertEquals(info.getSurname(), surname);

		String name1 = "John";
		String surname1 = "Lucas";
		info.setFirstName(name1);
		info.setSurname(surname1);
		assertNotEquals(info.getFirstName(), name);
		assertNotEquals(info.getSurname(), surname);
		assertEquals(info.getFirstName(), name1);
		assertEquals(info.getSurname(), surname1);

	}

	private void addressTest() {
		String street = "Pershore Road";
		String nr = "1165A";
		String city = "Birmingham";
		String pc = "B30 2YN";
		info.setStreet(street);
		info.setStreetNr(nr);
		info.setCity(city);
		info.setPostCode(pc);
		assertEquals(info.getStreet(), street);
		assertEquals(info.getStreetNr(), nr);
		assertEquals(info.getCity(), city);
		assertEquals(info.getPostCode(), pc);

		String street1 = "Bristol Road";
		String nr1 = "2564";
		String city1 = "Selly Oak";
		String pc1 = "B25 IN1";
		info.setStreet(street1);
		info.setStreetNr(nr1);
		info.setCity(city1);
		info.setPostCode(pc1);
		assertEquals(info.getStreet(), street1);
		assertEquals(info.getStreetNr(), nr1);
		assertEquals(info.getCity(), city1);
		assertEquals(info.getPostCode(), pc1);
	}

	private void contactsTest() {

		String email = "liutkusjok@gmail.com";
		String tel1 = "+447548001657";
		String tel2 = "+447548001658";
		String tel3 = "+447548001659";
		info.setEmail(email);
		info.setMainTel(tel1);
		info.setTelNr2(tel2);
		info.setTelNr3(tel3);

		assertEquals(info.getEmail(), email);
		assertEquals(info.getMainTel(), tel1);
		assertEquals(info.getTelNr2(), tel2);
		assertEquals(info.getTelNr3(), tel3);
	}

	private void userDetailsTest() {
		String user = "jokubas";
		String psw = "dhashdajk12312412";
		String secret = "jflashd1341435345";
		info.setUsername(user);
		info.setPassword(psw);
		info.setSecretWord(secret);
		assertEquals(info.getUsername(), user);
		assertEquals(info.getPassword(), psw);
		assertEquals(info.getSecretWord(), secret);
	}

	private void cardTypeTest() {
		for (CardType t : CardType.values()) {
			info.setType(t);
			assertEquals(info.getType(), t);
		}

	}

}
