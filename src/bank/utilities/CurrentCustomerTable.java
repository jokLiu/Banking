package bank.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bank.server.ServerHelper;

/**
 * The Class CurrentCustomerTable for storing all currently loged in customer.
 */
public class CurrentCustomerTable {
	
	/** The customer table. */
	Map<Integer, ArrayList<ServerHelper>> custTable = new HashMap<>();
	
	
	/**
	 * Adds the customer to the customer table.
	 *
	 * @param id the id
	 * @param helper the helper
	 */
	public void addCustomer(int id, ServerHelper helper)
	{
		if(custTable.containsKey(id))
		{
			ArrayList<ServerHelper> temp = custTable.get(id);
			temp.add(helper);
			custTable.put(id, temp);
		}
		else
		{
			ArrayList<ServerHelper> temp = new ArrayList<>();
			temp.add(helper);
			custTable.put(id,temp);
		}
			
	}
	
	/**
	 * Delete customer from the table when exits.
	 *
	 * @param id the id
	 */
	public void deleteCustomer(int id)
	{
		custTable.remove(id);
	}
	
	
	/**
	 * Gets the helper.
	 *
	 * @param id the id
	 * @return the helper
	 */
	public ArrayList<ServerHelper> getHelper(int id)
	{
		
		return custTable.get(id);
	}

	/**
	 *Checks if the customer is logged on.
	 *
	 * @param id the id
	 * @return true, if successful
	 */
	public boolean exists(int id)
	{
		return custTable.containsKey(id);
	}
}

