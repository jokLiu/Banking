package bank.utilities;

import java.io.Serializable;
import java.sql.Date;

public class Transaction implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 10603374327501157L;
	private int custID;
	private int toID;
	private String desc;
	private Date timeStamp;
	private double amount;
	
	public Transaction()
	{
		
	}
	public int getToID() {
		return toID;
	}
	public void setToID(int toID) {
		this.toID = toID;
	}
	public Transaction(int custID, String desc, Date timeStamp, double amount) {
		super();
		this.custID = custID;
		this.desc = desc;
		this.timeStamp = timeStamp;
		this.amount = amount;
	}
	
	public Transaction(int custID, int toID, String desc, Date timeStamp, double amount) {
		super();
		this.custID = custID;
		this.toID = toID;
		this.desc = desc;
		this.timeStamp = timeStamp;
		this.amount = amount;
	}


	public int getCustID() {
		return custID;
	}
	public void setCustID(int custID) {
		this.custID = custID;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
	
}
