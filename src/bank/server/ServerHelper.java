package bank.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Random;

import bank.server.database.ManageDatabase;
import bank.utilities.CustomerInformation;
import bank.utilities.LogIn;
import bank.utilities.Requests;

//Thread for dealing with a single customer
public class ServerHelper extends Thread {

	private ManageDatabase db;
	private ObjectInputStream fromCustomer;
	private ObjectOutputStream toCustomer;
	private char[] threeSecret = new char[3];
	private int[] threeBits = new int[3];
	private int id;

	public ServerHelper(ManageDatabase db, ObjectInputStream fromCustomer, ObjectOutputStream toCustomer) {
		this.db = db;
		this.fromCustomer = fromCustomer;
		this.toCustomer = toCustomer;
	}

	/*
	 * main run method waiting for request and acting upon certain request Runs
	 * until gets exit message
	 */
	public void run() {
		boolean running = true;
		Requests temp = null;
		while (running) {
			begin:
			// Waiting for the request from customer
			try {
				temp = (Requests) fromCustomer.readObject();
			} catch (ClassNotFoundException | IOException e) {
				break begin; // jump back to the begin of the loop
			}
			switch (temp) {
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
				depositAndWithdraw(temp);
				break;
			case Withdraw:
				depositAndWithdraw(temp);
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

	}

	// adding the new customer
	private void register() {
		try {
			/*
			 * getting the information object from customer If throws an
			 * exception then send the message back to the customer that
			 * registration was unsuccessful
			 */
			db.addAccount((CustomerInformation) fromCustomer.readObject());
			toCustomer.writeObject(Requests.RegisterSuccessful);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			try {
				toCustomer.writeObject(Requests.RegisterUnsuccessful);
			} catch (IOException e1) {

			}
		}
	}

	// customer log in
	private void logIn() {
		try {
			// getting the log in details from the customer
			LogIn logIn = (LogIn) fromCustomer.readObject();

			// getting the secret word based on user name and password
			String secret = db.isUserValid(logIn.getUsr(), logIn.getPsw());

			/*
			 * if secret word exists then we send it back to the customer to
			 * verify otherwise the log in was wrong and secret word was not
			 * received
			 */
			if (secret != null) {
				// getting and setting the id of the account
				id = db.getId(logIn.getUsr(), logIn.getPsw());

				// sending the request to the customer to deal with
				toCustomer.writeObject(Requests.Secret);

				// generating random
				int[] ints = new Random().ints(0, secret.length()).distinct().limit(3).toArray();
				Arrays.parallelSort(ints);
				threeBits = ints;

				// storing the letters of secret code for latter checking
				threeSecret[0] = secret.charAt(threeBits[0]);
				threeSecret[1] = secret.charAt(threeBits[1]);
				threeSecret[2] = secret.charAt(threeBits[2]);
				toCustomer.writeObject(threeBits);
			} else {
				toCustomer.writeObject(Requests.WrongLogIn);
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// When a secret request from customer is received
	private void secret() {
		try {
			// reading the char array of secret letters from customer
			char[] temp = (char[]) fromCustomer.readObject();

			// cheking if the secret letters are correct
			boolean check = Arrays.equals(temp, threeSecret);

			// if the secret letters are correct
			// then the log in is done
			// and send the confirmation request
			// and all the required information about the customer
			if (check) {
				toCustomer.writeObject(Requests.LogInValid);
				CustomerInformation info = db.getInformation(id);
				info.setId(id);
				toCustomer.writeObject(info); // ifnormation
				toCustomer.writeObject(db.getBalance(id)); // double
			} else {
				toCustomer.writeObject(Requests.WrongSecret);
			}
		} catch (ClassNotFoundException | IOException e) {
			// TODO
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// When deposit and withdraw money request is sent
	private void depositAndWithdraw(Requests r) {
		try {
			//receivng the amount to be deposited or withdrawn
			double deposit = (double) fromCustomer.readObject();
			
			//checking what request to fullfill
			if (r.equals(Requests.Deposit))
				db.depositMoney(id, deposit);
			else
				db.withdrawMoney(id, deposit);
			
			//update the customer
			toCustomer.writeObject(Requests.Update);
			toCustomer.writeObject(db.getBalance(id));
		} catch (ClassNotFoundException | IOException e) {
			//TODO
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//When transfer request is received
	private void transfer() {
		try {
			//getting recipient id
			int toId = (int) fromCustomer.readObject();
			
			//getting the amount to be transferred
			double amount = (double) fromCustomer.readObject();
			
			//updating the database
			db.transfer(id, toId, amount);
			
			//updating the customer
			toCustomer.writeObject(Requests.Update);
			toCustomer.writeObject(db.getBalance(id));
		} catch (ClassNotFoundException | IOException e) {
			//TODO
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}