
public class CustomerInformation {
	//name
	private Titles title;
	private String firstName;
	private String surname;
	
	//address
	private String street;
	private int streetNr;
	private String city;
	private String postCode;
	
	//contacts
	private String email;
	private String mainTel;
	private String telNr2;
	private String telNr3;
	
	//account
	private String username;
	private String password;
	private String secretWord;
	private CardType type;
	private int id;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CardType getType() {
		return type;
	}

	public void setType(CardType type) {
		this.type = type;
	}

	public Titles getTitle() {
		return title;
	}

	public void setTitle(Titles title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getStreetNr() {
		return streetNr;
	}

	public void setStreetNr(int streetNr) {
		this.streetNr = streetNr;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMainTel() {
		return mainTel;
	}

	public void setMainTel(String mainTel) {
		this.mainTel = mainTel;
	}

	public String getTelNr2() {
		return telNr2;
	}

	public void setTelNr2(String telNr2) {
		this.telNr2 = telNr2;
	}

	public String getTelNr3() {
		return telNr3;
	}

	public void setTelNr3(String telNr3) {
		this.telNr3 = telNr3;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecretWord() {
		return secretWord;
	}

	public void setSecretWord(String secretWord) {
		this.secretWord = secretWord;
	}
	
	
}
