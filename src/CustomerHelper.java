import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CustomerHelper extends Thread {
	
	private ObjectInputStream fromServer;
	private ObjectOutputStream toServer;
	
	public CustomerHelper(ObjectInputStream fromServer, ObjectOutputStream toServer)
	{
		this.fromServer = fromServer;
		this.toServer = toServer;
	}

	
	
	public void run()
	{
		boolean running = true;
		while(running)
		{
			
		}
	}
}
