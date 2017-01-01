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

//class for displaying the 
public class LogInView extends JFrame{

	
	private JTextField username;
	private JPasswordField password;
	private JButton logIn;
	private JButton register;
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer;
	public LogInView(ObjectOutputStream toServer, ObjectInputStream fromServer)
	{
		super("Bank Log In");
		
		this.toServer = toServer;
		this.fromServer = fromServer;
		username = new JTextField();
		password = new JPasswordField();
		logIn = new JButton("Log In");
		register = new JButton("Register");
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new GridLayout(2,2));
		northPanel.add(new JLabel ("User name: ", SwingConstants.RIGHT));
		northPanel.add(username);
		northPanel.add(new JLabel ("Password: ", SwingConstants.RIGHT));
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
	
	
	
	
	private void addLogInAction()
	{
		logIn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				String name = username.getText();
				char[] psw = password.getPassword();
				try {
					toServer.writeObject(Requests.LogIn);
					toServer.writeObject(new LogIn(name,String.valueOf(psw)));
					
					psw = new char[0];
					
					Requests r = (Requests)fromServer.readObject();
					switch(r)
					{
					case WrongLogIn:
						JOptionPane.showMessageDialog(new JFrame(),
						"Wrong username or password!",
						"Error",
						JOptionPane.WARNING_MESSAGE);
						break;
					case Secret:
						
						int[] secret = (int[])fromServer.readObject();
						EventQueue.invokeLater(() -> {
							JFrame frame = new SecretPasswordView(toServer, fromServer,secret);
							frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
							frame.setVisible(true);
						});
						dispose();
						break;
					}
				} catch (IOException | ClassNotFoundException e1) {
					e1.printStackTrace();
				}
			}
			
		});
	}
	
	
	private void addRegisterListener()
	{
		register.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				EventQueue.invokeLater(() -> {
					
					JFrame frame = new RegisterView(toServer, fromServer);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setVisible(true);
					dispose();
				});
				
			}
			
			
		});
	}
	
	
}
