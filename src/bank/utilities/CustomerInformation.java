package bank.utilities;
import java.io.Serializable;

/**
 * The Class CustomerInformation for keeping all the information about the customer.
 */
public class CustomerInformation implements Serializable{
	
	
	/** The title. */
	//name
	private Titles title;
	
	/** The first name. */
	private String firstName;
	
	/** The surname. */
	private String surname;
	
	/** The street. */
	//address
	private String street;
	
	/** The street nr. */
	private String streetNr;
	
	/** The city. */
	private String city;
	
	/** The post code. */
	private String postCode;
	
	/** The email. */
	//contacts
	private String email;
	
	/** The main tel. */
	private String mainTel;
	
	/** The tel nr2. */
	private String telNr2;
	
	/** The tel nr3. */
	private String telNr3;
	
	/** The username. */
	//account
	private String username;
	
	/** The password. */
	private String password;
	
	/** The secret word. */
	private String secretWord;
	
	/** The type. */
	private CardType type;
	
	/** The id. */
	private int id;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public CardType getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(CardType type) {
		this.type = type;
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public Titles getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the new title
	 */
	public void setTitle(Titles title) {
		this.title = title;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the surname.
	 *
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * Sets the surname.
	 *
	 * @param surname the new surname
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * Gets the street.
	 *
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Sets the street.
	 *
	 * @param street the new street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Gets the street nr.
	 *
	 * @return the street nr
	 */
	public String getStreetNr() {
		return streetNr;
	}

	/**
	 * Sets the street nr.
	 *
	 * @param streetNr the new street nr
	 */
	public void setStreetNr(String streetNr) {
		this.streetNr = streetNr;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the post code.
	 *
	 * @return the post code
	 */
	public String getPostCode() {
		return postCode;
	}

	/**
	 * Sets the post code.
	 *
	 * @param postCode the new post code
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the main tel.
	 *
	 * @return the main tel
	 */
	public String getMainTel() {
		return mainTel;
	}

	/**
	 * Sets the main tel.
	 *
	 * @param mainTel the new main tel
	 */
	public void setMainTel(String mainTel) {
		this.mainTel = mainTel;
	}

	/**
	 * Gets the tel nr2.
	 *
	 * @return the tel nr2
	 */
	public String getTelNr2() {
		return telNr2;
	}

	/**
	 * Sets the tel nr2.
	 *
	 * @param telNr2 the new tel nr2
	 */
	public void setTelNr2(String telNr2) {
		this.telNr2 = telNr2;
	}

	/**
	 * Gets the tel nr3.
	 *
	 * @return the tel nr3
	 */
	public String getTelNr3() {
		return telNr3;
	}

	/**
	 * Sets the tel nr3.
	 *
	 * @param telNr3 the new tel nr3
	 */
	public void setTelNr3(String telNr3) {
		this.telNr3 = telNr3;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the secret word.
	 *
	 * @return the secret word
	 */
	public String getSecretWord() {
		return secretWord;
	}

	/**
	 * Sets the secret word.
	 *
	 * @param secretWord the new secret word
	 */
	public void setSecretWord(String secretWord) {
		this.secretWord = secretWord;
	}
	
	
}
