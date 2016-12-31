import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class ServerHelper extends Thread{

	private ManageDatabase db;
	private ObjectInputStream fromCustomer;
	private ObjectOutputStream toCustomer;
	private char[] threeSecret = new char[3];
	private int[] threeBits = new int[3];
	private int id;
	public ServerHelper(ManageDatabase db, ObjectInputStream fromCustomer, ObjectOutputStream toCustomer)
	{
		this.db = db;
		this.fromCustomer = fromCustomer;
		this.toCustomer = toCustomer;
	}
	
	
	
	public void run()
	{
		boolean running = true;
		try {
			while(running){
				Requests temp = (Requests)fromCustomer.readObject();
				switch(temp){
				case Register:
					register();
					break;
				case LogIn:
					logIn();
					break;
				case Secret:
					secret();
					break;
				case Deposit:
					deposit();
					break;
				case Withdraw:
					withdraw();
					break;
				case Transfer:
					transfer();
					break;
				case Exit:
					running = !running;
					break;
				default:
					break;
					
				}
			}
		
		
		} catch (ClassNotFoundException | IOException e) {
			
			e.printStackTrace();
		}
	}
	
	//adding the new customer
	private void register()
	{
		try {
			db.addAccount((CustomerInformation) fromCustomer.readObject());
			toCustomer.writeObject(Requests.RegisterSuccessful);
		} catch (ClassNotFoundException | IOException e) {
			try {
				toCustomer.writeObject(Requests.RegisterUnsuccessful);
			} catch (IOException e1) {

			}
		}
	}
	
	//customer log in
	private void logIn()
	{
		try {
			LogIn logIn = (LogIn) fromCustomer.readObject();
			String secret = db.isUserValid(logIn.getUsr(), logIn.getPsw());
			
			System.out.println(secret);
			if(secret != null){
				id = db.getId(logIn.getUsr(), logIn.getPsw());
				toCustomer.writeObject(Requests.Secret);
				Random rand = new Random();
				threeBits[0] = rand.nextInt(secret.length());
				threeSecret[0] = secret.charAt(threeBits[0]);
				threeBits[1] = rand.nextInt(secret.length());
				threeSecret[1] = secret.charAt(threeBits[1]);
				threeBits[2] = rand.nextInt(secret.length());
				threeSecret[2] = secret.charAt(threeBits[2]);
				toCustomer.writeObject(threeBits);
			}
			else{
				toCustomer.writeObject(Requests.WrongLogIn);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void secret()
	{
		try {
			char[] temp = (char[]) fromCustomer.readObject();
			
			boolean check = true;
			for(int i=0; i<3; i++)
			{
				if(temp[i] != threeSecret[i])
				{
					check = false;
					break;
				}
			}
			if(check)
			{
				System.out.println("yey");
				toCustomer.writeObject(Requests.LogInValid);
				CustomerInformation info = db.getInformation(id);
				info.setId(id);
				toCustomer.writeObject(info); //ifnormation
				toCustomer.writeObject(db.getBalance(id)); //double
			}
			else
			{
				System.out.println("no");
				toCustomer.writeObject(Requests.WrongSecret);
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void deposit()
	{
		try {
			double deposit = (double)fromCustomer.readObject();
			db.depositMoney(id, deposit);
			toCustomer.writeObject(Requests.Update);
			toCustomer.writeObject(db.getBalance(id));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	
	private void withdraw()
	{
		try {
			double deposit = (double)fromCustomer.readObject();
			db.withdrawMoney(id, deposit);
			toCustomer.writeObject(Requests.Update);
			toCustomer.writeObject(db.getBalance(id));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void transfer()
	{
		try {
			int toId = (int)fromCustomer.readObject();
			double deposit = (double)fromCustomer.readObject();
			db.transfer(id, toId, deposit);
			toCustomer.writeObject(Requests.Update);
			toCustomer.writeObject(db.getBalance(id));
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
}
