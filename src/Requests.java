import java.io.Serializable;

public enum Requests implements Serializable{
	Register, LogIn, Secret, WrongLogIn, LogInValid, WrongSecret, 
	Deposit, Withdraw, Transfer, ActionHistory, Update, 
	Exit, RegisterSuccessful, RegisterUnsuccessful
}
