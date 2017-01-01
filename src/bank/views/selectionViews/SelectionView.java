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

public class SelectionView extends JFrame {

	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	private CustomerInformation info;
	public double balance;
	private JButton deposit;
	private JButton withdraw;
	private JButton transfer;
	private JButton details;
	private JButton history;
	private JButton exit;
	private JPanel panel;
	private JPanel mainPanel;
	private JLabel bal;
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
		// TODO Sort out the history
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
		
		Thread t = new TransferHelper(fromServer, toServer,this);
		t.start();
		mainPanel.add(panel, BorderLayout.CENTER);
		panel.add(exit);
		add(mainPanel);
		pack();

	}
	
	
	public void upd()
	{
		
		bal = new JLabel(String.valueOf(balance));
		panel.remove(3);
		panel.add(bal,3);
		mainPanel.add(panel, BorderLayout.CENTER);
		add(mainPanel);
		pack();

		
	}

	private void addListener(JButton button, Requests r) {
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					
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
							frame = new TransferView(toServer, balance);
							break;
						case Details:
							frame = new AccountDetailsView(info);
							break;
						}

						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setVisible(true);

					});

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		});
	}
}
