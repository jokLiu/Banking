package bank.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import bank.server.database.ManageDatabase;
import bank.utilities.CurrentCustomerTable;
import bank.utilities.CustomerInformation;
import bank.utilities.LogIn;
import bank.utilities.Requests;
import bank.utilities.Transaction;

/**
 * The Class ServerHelper.
 * Thread for dealing with a single customer
 */
public class ServerHelper extends Thread {

	/** The database. */
	private ManageDatabase db;
	
	/** The from customer. */
	private ObjectInputStream fromCustomer;
	
	/** The to customer. */
	private ObjectOutputStream toCustomer;
	
	/** The customers. */
	private CurrentCustomerTable customers;
	
	/** The three chars of secret. */
	private char[] threeSecret = new char[3];
	
	/** The three bits of secret password. */
	private int[] threeBits = new int[3];
	
	/** The id. */
	private int id;

	/**
	 * Instantiates a new server helper.
	 *
	 * @param db the db
	 * @param fromCustomer the from customer
	 * @param toCustomer the to customer
	 * @param customers the customers
	 */
	public ServerHelper(ManageDatabase db, ObjectInputStream fromCustomer, ObjectOutputStream toCustomer,
			CurrentCustomerTable customers) {
		this.db = db;
		this.fromCustomer = fromCustomer;
		this.toCustomer = toCustomer;
		this.customers = customers;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
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
			case ActionHistory:
				actionHistory();
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
			case UserExists:
				userExists();
				break;
			default:
				break;

			}
		}

	}

	/**
	 * Register.
	 * adding the new customer
	 */
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

	/**
	 * Log in.
	 * customer log in
	 */
	
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
		} catch (ClassNotFoundException | IOException | SQLException e) {
			//If exception is thrown then do nothing and wait for the customer 
		} 
	}

	/**
	 * Secret.
	 * When a secret request from customer is received
	 */
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
				customers.addCustomer(id, this);
				toCustomer.writeObject(info); // information
				toCustomer.writeObject(db.getBalance(id)); // double
			} else {
				toCustomer.writeObject(Requests.WrongSecret);
			}
		} catch (ClassNotFoundException | IOException |SQLException  e) {
			//If exception is thrown then do nothing and wait for the customer
		} 
	}

	/**
	 * Deposit and withdraw.
	 * When deposit and withdraw money request is sent
	 * 
	 * @param r the r
	 */
	private void depositAndWithdraw(Requests r) {
		try {
			// receivng the amount to be deposited or withdrawn
			double deposit = (double) fromCustomer.readObject();

			// checking what request to fullfill
			if (r.equals(Requests.Deposit))
				db.depositMoney(id, deposit);
			else
				db.withdrawMoney(id, deposit);

			// update the customer
			for (ServerHelper h : customers.getHelper(id))
				h.updateCustomer();
			updateCustomer();
		} catch (ClassNotFoundException | IOException | SQLException e) {
			//If exception is thrown then do nothing and wait for the customer
		} 
	}

	/**
	 * Transfer.
	 * When transfer request is received
	 */
	private void transfer() {
		try {
			// getting recipient id
			int toId = (int) fromCustomer.readObject();

			// getting the amount to be transferred
			double amount = (double) fromCustomer.readObject();

			// updating the database
			db.transfer(id, toId, amount);

			// updating the customer
			if (customers.exists(toId)) {
				for (ServerHelper h : customers.getHelper(toId))
					h.updateCustomer();
			}
			if(customers.exists(id))
			{
				for (ServerHelper h : customers.getHelper(id))
					h.updateCustomer();
			}
		} catch (ClassNotFoundException | IOException |SQLException e) {
			//If exception is thrown then do nothing and wait for the customer
			} 
	}
	
	
	/**
	 * Action history.
	 */
	private void actionHistory()
	{
		try {
			// update the customer
			
			ArrayList<Transaction> deposits = db.getDepositsAndWithdrawals(id);
			ArrayList<Transaction> transfers = db.getTransfers(id);
			toCustomer.writeObject(Requests.ActionHistory);
			toCustomer.writeObject(deposits);
			toCustomer.writeObject(transfers);
		} catch (IOException | SQLException e) {
			// If exception is thrown then do nothing and wait for the customer
		} 
	}

	/**
	 * User exists.
	 */
	private void userExists() {
		try {
			// update the customer
			int toId = (int) fromCustomer.readObject();
			boolean check = db.isUserValid(toId);
			toCustomer.writeObject(Requests.UserExists);
			toCustomer.writeObject(check);
		} catch (IOException | SQLException | ClassNotFoundException e) {
			// If exception is thrown then do nothing and wait for the customer
		} 
	}

	/**
	 * Update customer.
	 */
	// updating another customer which is currently loged in
	public void updateCustomer() {
		try {
			toCustomer.writeObject(Requests.Update);
			toCustomer.writeObject(db.getBalance(id));
		} catch (IOException | SQLException e) {
//			If exception is thrown then do nothing and wait for the customer
		} 
	}
}
