package bank.views.selectionViews;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import bank.customer.TransferHelper;
import bank.utilities.CustomerInformation;
import bank.utilities.Requests;

// TODO: Auto-generated Javadoc
/**
 * The Class SelectionView.
 */
public class SelectionView extends JFrame {

	/** The to server. */
	private ObjectOutputStream toServer;
	
	/** The from server. */
	private ObjectInputStream fromServer;
	
	/** The info. */
	private CustomerInformation info;
	
	/** The balance. */
	public double balance;
	
	/** The deposit. */
	private JButton deposit;
	
	/** The withdraw. */
	private JButton withdraw;
	
	/** The transfer. */
	private JButton transfer;
	
	/** The details. */
	private JButton details;
	
	/** The history. */
	private JButton history;
	
	/** The exit. */
	private JButton exit;
	
	/** The panel. */
	private JPanel panel;
	
	/** The main panel. */
	private JPanel mainPanel;
	
	/** The bal. */
	private JLabel bal;

	/**
	 * Instantiates a new selection view.
	 *
	 * @param toServer the to server
	 * @param fromServer the from server
	 * @param info the info
	 * @param balance the balance
	 */
	public SelectionView(ObjectOutputStream toServer, ObjectInputStream fromServer, CustomerInformation info,
			double balance) {
		super("Bank");

		this.toServer = toServer;
		this.fromServer = fromServer;
		this.info = info;
		this.balance = balance;

		mainPanel = new JPanel(new BorderLayout());
		panel = new JPanel(new GridLayout(5, 2, 5, 5));

		mainPanel.add(new JLabel(info.getFirstName() + ", welcome to your Banking System!"), BorderLayout.NORTH);

		panel.add(new JLabel("Your id : "));
		panel.add(new JLabel(String.valueOf(info.getId())));

		bal = new JLabel(String.valueOf(balance));
		panel.add(new JLabel("Balance : "));
		panel.add(bal);

		deposit = new JButton("Deposit Money");
		addListener(deposit, Requests.Deposit);
		panel.add(deposit);

		withdraw = new JButton("Withdraw Money");
		addListener(withdraw, Requests.Withdraw);
		panel.add(withdraw);

		transfer = new JButton("Transfer Money");
		addListener(transfer, Requests.Transfer);
		panel.add(transfer);

		details = new JButton("Account details");
		addListener(details, Requests.Details);
		panel.add(details);

		history = new JButton("Payment history");
		addListener(history, Requests.ActionHistory);
		panel.add(history);

		exit = new JButton("Exit");
		exit.addActionListener(e -> {
			try {
				toServer.writeObject(Requests.Exit);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.exit(1);
		});

		Thread t = new TransferHelper(fromServer, toServer, this);
		t.start();
		mainPanel.add(panel, BorderLayout.CENTER);
		panel.add(exit);
		add(mainPanel);
		pack();

	}

	/**
	 * Upd.
	 */
	public void upd() {

		bal = new JLabel(String.valueOf(balance));
		panel.remove(3);
		panel.add(bal, 3);
		mainPanel.add(panel, BorderLayout.CENTER);
		add(mainPanel);
		pack();

	}

	/**
	 * Adds the listener.
	 *
	 * @param button the button
	 * @param r the request
	 */
	private void addListener(JButton button, Requests r) {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (r.equals(Requests.ActionHistory)) {

						toServer.writeObject(r);

					} else {
						EventQueue.invokeLater(() -> {
							JFrame frame = null;
							switch (r) {
							case Deposit:
								frame = new DepositWithdrawView(toServer, r, "Amount to deposit: ", balance);
								break;
							case Withdraw:
								frame = new DepositWithdrawView(toServer, r, "Amount to withdraw: ", balance);
								break;
							case Transfer:
								frame = new TransferView(toServer, fromServer, balance);
								break;
							case Details:
								frame = new AccountDetailsView(info);
								break;
							}

							frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							frame.setVisible(true);

						});
					}

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
	}
}
