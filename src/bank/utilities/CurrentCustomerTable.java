package bank.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bank.server.ServerHelper;

//class for storing all currently loged in customer
public class CurrentCustomerTable {
	Map<Integer, ArrayList<ServerHelper>> custTable = new HashMap<>();
	
	public CurrentCustomerTable()
	{
		
	}

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
	
	public void deleteCustomer(int id)
	{
		//TODO fix the value
		custTable.remove(id);
	}
	
	public ArrayList<ServerHelper> getHelper(int id)
	{
		
		return custTable.get(id);
	}

	public boolean exists(int id)
	{
		return custTable.containsKey(id);
	}
}

