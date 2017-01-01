package bank.utilities;
import java.io.Serializable;

//requests for easier communication between customer and server 
//via socket to avoid incorrect spelling of Strings
public enum Requests implements Serializable{
	Register, LogIn, Secret, WrongLogIn, LogInValid, WrongSecret, 
	Deposit, Withdraw, Transfer, ActionHistory, Update, Details,
	Exit, RegisterSuccessful, RegisterUnsuccessful
}
