package bank.views.selectionViews;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bank.utilities.Requests;

public class TransferView extends JFrame {
	private ObjectOutputStream toServer;
	private JTextField amount;
	private JTextField accId;
	private JButton transfer;
	private JButton cancel;
	private double balance;

	public TransferView(ObjectOutputStream toServer, double balance) {
		super("Transfer Window");

		this.toServer = toServer;
		this.balance = balance;
		JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
		panel.add(new JLabel("Balance: "));
		panel.add(new JLabel(String.valueOf(balance)));
		panel.add(new JLabel("Amount to transfer: "));
		amount = new JTextField();
		panel.add(amount);

		panel.add(new JLabel("Recipient ID: "));
		accId = new JTextField();
		panel.add(accId);

		transfer = new JButton("Transfer");
		addTransferListener();
		panel.add(transfer);

		cancel = new JButton("Cancel");
		cancel.addActionListener(e -> dispose());
		panel.add(cancel);

		add(panel, BorderLayout.CENTER);
		pack();

	}

	private void addTransferListener() {
		transfer.addActionListener(e -> {
			int id = Integer.valueOf(accId.getText());
			double money = Integer.valueOf(amount.getText());
			//TODO check if id exists
			if (money > balance) {
				JOptionPane.showMessageDialog(new JFrame(), "Insufficient balance!", "Error",
						JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					toServer.writeObject(Requests.Transfer);
					toServer.writeObject(id);
					toServer.writeObject(money);
				} catch (IOException e1) {
					// TODO require password
					e1.printStackTrace();
				}
				dispose();
			}
		});

	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			ObjectOutputStream toCustomer = null;
			ObjectInputStream fromServer = null;
			JFrame frame = new TransferView(toCustomer, 128.5);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}
