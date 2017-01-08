package bank.utilities;

import java.io.Serializable;
import java.sql.Date;


/**
 * The Class for keeping Transaction.
 */
public class Transaction implements Serializable{
	
	/** The cust id. */
	private int custID;
	
	/** The to customer id. */
	private int toID;
	
	/** The descricption */
	private String desc;
	
	/** The time stamp. */
	private Date timeStamp;
	
	/** The amount. */
	private double amount;
	
	/**
	 * Instantiates a new transaction.
	 */
	public Transaction()
	{
		
	}
	
	/**
	 * Gets the to id.
	 *
	 * @return the to id
	 */
	public int getToID() {
		return toID;
	}
	
	/**
	 * Sets the to id.
	 *
	 * @param toID the new to id
	 */
	public void setToID(int toID) {
		this.toID = toID;
	}
	
	/**
	 * Instantiates a new transaction.
	 *
	 * @param custID the cust id
	 * @param desc the desc
	 * @param timeStamp the time stamp
	 * @param amount the amount
	 */
	public Transaction(int custID, String desc, Date timeStamp, double amount) {
		super();
		this.custID = custID;
		this.desc = desc;
		this.timeStamp = timeStamp;
		this.amount = amount;
	}
	
	/**
	 * Instantiates a new transaction.
	 *
	 * @param custID the cust id
	 * @param toID the to id
	 * @param desc the desc
	 * @param timeStamp the time stamp
	 * @param amount the amount
	 */
	public Transaction(int custID, int toID, String desc, Date timeStamp, double amount) {
		super();
		this.custID = custID;
		this.toID = toID;
		this.desc = desc;
		this.timeStamp = timeStamp;
		this.amount = amount;
	}


	/**
	 * Gets the cust id.
	 *
	 * @return the cust id
	 */
	public int getCustID() {
		return custID;
	}
	
	/**
	 * Sets the cust id.
	 *
	 * @param custID the new cust id
	 */
	public void setCustID(int custID) {
		this.custID = custID;
	}
	
	/**
	 * Gets the desc.
	 *
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	
	/**
	 * Sets the desc.
	 *
	 * @param desc the new desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	/**
	 * Gets the time stamp.
	 *
	 * @return the time stamp
	 */
	public Date getTimeStamp() {
		return timeStamp;
	}
	
	/**
	 * Sets the time stamp.
	 *
	 * @param timeStamp the new time stamp
	 */
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	
	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	
}
