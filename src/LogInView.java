import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LogInView extends JFrame{

	
	private JTextField username;
	private JPasswordField password;
	private JButton logIn;
	private JButton register;
	private ObjectOutputStream toServer;
	
	public LogInView(ObjectOutputStream toServer)
	{
		super("Bank Log In");
		
		this.toServer = toServer;
		
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
		
		
		pack();
		
		
		/*
		setSize(300,200);
		setLocation(500,280);
		panel.setLayout (null); 
		
		
		
		addLogInAction();
		
		panel.add(username);
		panel.add(password);
		panel.add(logIn);
		panel.add(register);
	
		add(panel);
		getContentPane().add(panel);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		
		password = new JPasswordField(15);
	    getContentPane().add(password);

	    username = new JTextArea(1,15);
	    getContentPane().add(username);
	   

	    JLabel lblUsername = new JLabel("Username");
	    getContentPane().add(lblUsername);

	    JLabel lblPassword = new JLabel("Password");
	    getContentPane().add(lblPassword);

	    logIn = new JButton("Login");
	    getContentPane().add(logIn);

	    register = new JButton("New User ?");
	    getContentPane().add(register);
	    
	    panel.add(username);
	    panel.add(password);
	    panel.add(logIn);
	    panel.add(register);
	    panel.add(lblUsername);
	    panel.add(lblPassword);
	    
	    add(panel);
	    setSize(300,300);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();*/
		
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
					toServer.writeObject(new LogIn<String,String>(name,psw.toString()));
					psw = new char[0];
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});
	}
	
	
	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			ObjectOutputStream toCustomer = null;
			JFrame frame = new LogInView(toCustomer);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		});
	}
}
