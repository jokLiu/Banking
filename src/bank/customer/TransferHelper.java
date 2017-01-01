package bank.customer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import bank.utilities.Requests;
import bank.views.selectionViews.SelectionView;

public class TransferHelper extends Thread {
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private SelectionView view;
	public TransferHelper(ObjectInputStream fromServer, ObjectOutputStream toServer,
			SelectionView view)
	{
		this.fromServer = fromServer;
		this.toServer = toServer;
		this.view = view;
	}

	
	
	public void run()
	{
		boolean running = true;
		while(running)
		{
			try {
				Requests r = (Requests)fromServer.readObject();
				System.out.println("got it");
				double balance =(double)fromServer.readObject();
				view.balance = balance;
				view.upd();
				
			
			} catch (ClassNotFoundException | IOException e) {
				
				e.printStackTrace();
			}
			
		}
	}

}
