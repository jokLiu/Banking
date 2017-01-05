package bank.customer;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFrame;

import bank.utilities.Requests;
import bank.utilities.Transaction;
import bank.views.selectionViews.PaymentHistory;
import bank.views.selectionViews.SelectionView;

public class TransferHelper extends Thread {
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private SelectionView view;

	public TransferHelper(ObjectInputStream fromServer, ObjectOutputStream toServer, SelectionView view) {
		this.fromServer = fromServer;
		this.toServer = toServer;
		this.view = view;
	}

	public void run() {
		boolean running = true;
		while (running) {
			try {

				Requests r = (Requests)fromServer.readObject();
				System.out.println("got it");
				if (r.equals(Requests.Update)) {
					double balance = (double) fromServer.readObject();
					view.balance = balance;
					view.upd();
				}
				else if(r.equals(Requests.ActionHistory))
				{
					ArrayList<Transaction> deposits = (ArrayList<Transaction>) fromServer.readObject();
					ArrayList<Transaction> transfers = (ArrayList<Transaction>) fromServer.readObject();
				
					EventQueue.invokeLater(() -> {
						
						JFrame frame = new PaymentHistory(deposits, transfers);
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setVisible(true);
						frame.pack();
					});
				}
				else Thread.sleep(100);

			} catch (ClassNotFoundException | IOException e) {

				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
