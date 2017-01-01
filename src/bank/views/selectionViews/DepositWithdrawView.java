package bank.views.selectionViews;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class DepositWithdrawView extends JFrame {

	private ObjectOutputStream toServer;
	private JTextField amount;
	private JButton deposit;
	private JButton cancel;
	private double balance;

	public DepositWithdrawView(ObjectOutputStream toServer,  Requests r, String reason,
			double balance) {
		super(String.valueOf(r));

		this.toServer = toServer;
		this.balance = balance;
		JPanel panel = new JPanel(new GridLayout(3, 2));
		panel.add(new JLabel("Balance: "));
		panel.add(new JLabel(String.valueOf(balance)));
		panel.add(new JLabel(reason));

		amount = new JTextField();
		panel.add(amount);

		deposit = new JButton(String.valueOf(r));
		addDepositListener(r);
		panel.add(deposit);

		cancel = new JButton("Cancel");
		cancel.addActionListener(e -> dispose());
		panel.add(cancel);

		add(panel, BorderLayout.CENTER);
		pack();

	}

	private void addDepositListener(Requests r) {
		deposit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				double money = Double.valueOf(amount.getText());
				if (r.equals(Requests.Deposit)) {
					if (money < 0) {
						JOptionPane.showMessageDialog(new JFrame(), "Insufficient balance!", "Error",
								JOptionPane.WARNING_MESSAGE);
					}
					else
					{
						try {
							toServer.writeObject(r);
							toServer.writeObject(money);
						} catch (IOException e1) {
							// TODO require password
							e1.printStackTrace();
						}
						dispose();
					}
				}
				else
				{
					if (money > balance) {
						JOptionPane.showMessageDialog(new JFrame(), "Insufficient balance!", "Error",
								JOptionPane.WARNING_MESSAGE);
					}
					else
					{
						try {
							toServer.writeObject(r);
							toServer.writeObject(money);
						} catch (IOException e1) {
							// TODO require password
							e1.printStackTrace();
						}
						dispose();
					}
				}
			}

		});
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			ObjectOutputStream toCustomer = null;
			ObjectInputStream fromServer = null;
			JFrame frame = new DepositWithdrawView(toCustomer, Requests.Deposit, "Amount to deposit: ", 128.5);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}
