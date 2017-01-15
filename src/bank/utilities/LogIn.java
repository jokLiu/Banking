package bank.utilities;
import java.io.Serializable;


// TODO: Auto-generated Javadoc
/**
 * The Class for keeping Log-in details.
 */
public class LogIn implements Serializable {
	
	/** The username. */
	private String usr;
	
	/** The psw. */
	private String psw;
	
	
	/**
	 * Instantiates a new log in.
	 */
	public LogIn()
	{
		
	}
	/**
	 * Instantiates a new log in.
	 *
	 * @param usr the user name
	 * @param psw the password
	 */
	public LogIn(String usr, String psw)
	{
		this.usr = usr;
		this.psw = psw;
	}

	/**
	 * Sets the usr.
	 *
	 * @param usr the new usr
	 */
	public void setUsr(String usr) {
		this.usr = usr;
	}

	/**
	 * Sets the psw.
	 *
	 * @param psw the new psw
	 */
	public void setPsw(String psw) {
		this.psw = psw;
	}

	/**
	 * Gets the usrername.
	 *
	 * @return the usr
	 */
	public String getUsr() {
		return usr;
	}

	/**
	 * Gets the password.
	 *
	 * @return the psw
	 */
	public String getPsw() {
		return psw;
	}
}
