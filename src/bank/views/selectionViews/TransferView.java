package bank.views.selectionViews;

import java.awt.BorderLayout;
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
	private ObjectInputStream fromServer;
	private JTextField amount;
	private JTextField accId;
	private JButton transfer;
	private JButton cancel;
	private double balance;

	public TransferView(ObjectOutputStream toServer, ObjectInputStream fromServer, double balance) {
		super("Transfer Window");

		this.toServer = toServer;
		this.fromServer = fromServer;
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

			boolean valid = true;
			int id = 0;
			double money = 0;

			try {
				id = Integer.valueOf(accId.getText());
			} catch (NumberFormatException e2) {
				valid = false;
				errorWindow("Account id is represented by integer. \n Enter correct id! ");

			}

			try {
				money = Double.valueOf(amount.getText());
			} catch (NumberFormatException e2) {
				valid = false;
				errorWindow("Amount is represented by real number. \n Enter correct amount! ");

			}

			boolean recipientExists = false;
			if(valid)
			{
				try {
					toServer.writeObject(Requests.UserExists);
					toServer.writeObject(id);
					Thread.sleep(100);
					System.out.println("view");
					recipientExists = (boolean) fromServer.readObject();
					System.out.println("view2");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
			if(!recipientExists)
				errorWindow("Recipient does not exist! \n Please check ID! ");
			
			if (recipientExists && valid && money > balance ) {
				errorWindow("Insufficient balance!");
			} else if( recipientExists && valid){
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

	private void errorWindow(String msg) {
		JOptionPane.showMessageDialog(new JFrame(), msg, "Error", JOptionPane.WARNING_MESSAGE);
	}

}
