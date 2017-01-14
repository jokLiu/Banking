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

/**
 * The Class TransferHelper. Thread waiting for commands from server
 */
public class TransferHelper extends Thread {

	/** The to server. */
	private ObjectOutputStream toServer;

	/** The from server. */
	private ObjectInputStream fromServer;

	/** The view. */
	private SelectionView view;

	/**
	 * Instantiates a new transfer helper.
	 *
	 * @param fromServer
	 *            the from server
	 * @param toServer
	 *            the to server
	 * @param view
	 *            the view
	 */
	public TransferHelper(ObjectInputStream fromServer, ObjectOutputStream toServer, SelectionView view) {
		this.fromServer = fromServer;
		this.toServer = toServer;
		this.view = view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		boolean running = true;
		while (running) {
			try {

				// waiting for request
				Requests r = (Requests) fromServer.readObject();

				// updating the balance
				if (r.equals(Requests.Update)) {
					double balance = (double) fromServer.readObject();
					view.balance = balance;
					view.upd();
				}

				// displaying the history
				else if (r.equals(Requests.ActionHistory)) {
					ArrayList<Transaction> deposits = (ArrayList<Transaction>) fromServer.readObject();
					ArrayList<Transaction> transfers = (ArrayList<Transaction>) fromServer.readObject();

					EventQueue.invokeLater(() -> {

						JFrame frame = new PaymentHistory(deposits, transfers);
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setVisible(true);
						frame.pack();
					});
				} else
					Thread.sleep(100);

			} catch (ClassNotFoundException | IOException | InterruptedException e) {

				System.exit(1);
			}

		}
	}

}
