import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
				double balance =(double)fromServer.readObject();
				view.balance = balance;
				view.validate();
				view.repaint();
			
			} catch (ClassNotFoundException | IOException e) {
				
				e.printStackTrace();
			}
			
		}
	}

}
