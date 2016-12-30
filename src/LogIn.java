import java.io.Serializable;

public class LogIn implements Serializable {
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
