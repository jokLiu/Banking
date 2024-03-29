package bank.views;

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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import bank.utilities.LogIn;
import bank.utilities.Requests;

/**
 * The Class class for displaying the Log in windows View.
 */

public class LogInView extends JFrame {

	/** The username. */
	private JTextField username;

	/** The password. */
	private JPasswordField password;

	/** The log in. */
	private JButton logIn;

	/** The register. */
	private JButton register;

	/** The to server. */
	private ObjectOutputStream toServer;

	/** The from server. */
	private ObjectInputStream fromServer;

	/**
	 * Instantiates a new log in view.
	 *
	 * @param toServer
	 *            the to server
	 * @param fromServer
	 *            the from server
	 */
	public LogInView(ObjectOutputStream toServer, ObjectInputStream fromServer) {
		super("Bank Log In");

		this.toServer = toServer;
		this.fromServer = fromServer;
		username = new JTextField();
		password = new JPasswordField();
		logIn = new JButton("Log In");
		register = new JButton("Register");

		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(2, 2));
		northPanel.add(new JLabel("User name: ", SwingConstants.RIGHT));
		northPanel.add(username);
		northPanel.add(new JLabel("Password: ", SwingConstants.RIGHT));
		northPanel.add(password);
		add(northPanel, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		southPanel.add(logIn);
		southPanel.add(register);
		add(southPanel, BorderLayout.SOUTH);

		addLogInAction();
		addRegisterListener();
		pack();

	}

	/**
	 * Adds the log in action.
	 */
	private void addLogInAction() {
		logIn.addActionListener(e ->

		{
			String name = username.getText();
			char[] psw = password.getPassword();
			try {
				toServer.writeObject(Requests.LogIn);
				toServer.writeObject(new LogIn(name, String.valueOf(psw)));

				psw = new char[0];

				Requests r = (Requests) fromServer.readObject();
				switch (r) {
				case WrongLogIn:
					JOptionPane.showMessageDialog(new JFrame(), "Wrong username or password!", "Error",
							JOptionPane.WARNING_MESSAGE);
					break;
				case Secret:

					int[] secret = (int[]) fromServer.readObject();
					EventQueue.invokeLater(() -> {
						JFrame frame = new SecretPasswordView(toServer, fromServer, secret);
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setVisible(true);
					});
					dispose();
					break;
				}
			} catch (IOException | ClassNotFoundException e1) {
				System.exit(1);
			}
		}

		);
	}

	/**
	 * Adds the register listener.
	 */
	private void addRegisterListener() {
		register.addActionListener(e -> {
			EventQueue.invokeLater(() -> {

				JFrame frame = new RegisterView(toServer, fromServer);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				dispose();
			});

		}

		);
	}

}
