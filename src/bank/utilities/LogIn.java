package bank.utilities;
import java.io.Serializable;

public class LogIn implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -585613593593171670L;
	private String usr;
	private String psw;
	
	public LogIn(String usr, String psw)
	{
		this.usr = usr;
		this.psw = psw;
	}

	public String getUsr() {
		return usr;
	}

	public String getPsw() {
		return psw;
	}
}
