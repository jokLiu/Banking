
public class LogIn<User extends Comparable<User>,Psw extends Comparable<Psw>> {
	private User usr;
	private Psw psw;
	
	public LogIn(User usr, Psw psw)
	{
		this.usr = usr;
		this.psw = psw;
	}

	public User getUsr() {
		return usr;
	}

	public Psw getPsw() {
		return psw;
	}
}
