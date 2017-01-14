package bank.views;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import bank.utilities.CustomerInformation;
import bank.utilities.Requests;
import bank.views.selectionViews.SelectionView;

/**
 * The Class SecretPasswordView.
 */
public class SecretPasswordView extends JFrame {
	
	/** The first. */
	private JComboBox<String> first;
	
	/** The second. */
	private JComboBox<String> second;
	
	/** The third. */
	private JComboBox<String> third;
	
	/** The login. */
	private JButton login;
	
	/** The to server. */
	private ObjectOutputStream toServer;
	
	/** The from server. */
	private ObjectInputStream fromServer; 
	
	/**
	 * Instantiates a new secret password view.
	 *
	 * @param toServer the to server
	 * @param fromServer the from server
	 * @param elem the elem
	 */
	public SecretPasswordView(ObjectOutputStream toServer, ObjectInputStream fromServer, int[] elem)
	{
		super("Secret Word Verification");
		
		this.toServer = toServer;
		this.fromServer = fromServer;
		
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.add((new JLabel("Letter From Secret Password")),BorderLayout.PAGE_START);
		
		first = new JComboBox<>();
		second = new JComboBox<>();
		third = new JComboBox<>();
		for(char i ='a'; i<= 'z'; i++)
		{
			first.addItem(String.valueOf(i));
			second.addItem(String.valueOf(i));
			third.addItem(String.valueOf(i));
		}
		
		for(int i=0; i<=9; i++)
		{
			first.addItem(String.valueOf(i));
			second.addItem(String.valueOf(i));
			third.addItem(String.valueOf(i));
		}
		
		JPanel panelmain = new JPanel(new FlowLayout());
		
		JPanel panelWest = new JPanel(new GridLayout(2,1));
		JLabel labelWest = new JLabel(String.valueOf(elem[0]));
		panelWest.add(labelWest);
		panelWest.add(first);
		panelmain.add(panelWest);
	
		JPanel panelMiddle = new JPanel(new GridLayout(2,1));
		JLabel labelMiddle = new JLabel(String.valueOf(elem[1]));
		panelMiddle.add(labelMiddle);
		panelMiddle.add(second);
		panelmain.add(panelMiddle, BorderLayout.CENTER);
		
		JPanel panelEast = new JPanel(new GridLayout(2,1));
		JLabel labelEast = new JLabel(String.valueOf(elem[2]));
		panelEast.add(labelEast);
		panelEast.add(third);
		panelmain.add(panelEast, BorderLayout.EAST);
		
		panel.add(panelmain, BorderLayout.CENTER);
		
		
		login = new JButton("Log In");
		JButton exit = new JButton("Exit");
		exit.addActionListener(e -> {
			try {
				toServer.writeObject(Requests.Exit);
				toServer.close();
				fromServer.close();
			} catch (Exception e1) {
				
			}
			finally
			{
				System.exit(1);
			}
			
		});
		
		JPanel last = new JPanel(new GridLayout(1,2, 7, 7));
		last.add(login);
		last.add(exit);

		panel.add(last, BorderLayout.AFTER_LAST_LINE);
		addListenerLogIn();
		
		add(panel);
		pack();
		
		
	}
	
	/**
	 * Adds the listener log in.
	 */
	private void addListenerLogIn()
	{
		login.addActionListener(new ActionListener()
				{

					@Override
					public void actionPerformed(ActionEvent e) {
						char[] values = new char[3];
						values[0] = ((String)first.getSelectedItem()).charAt(0);
						values[1] = ((String)second.getSelectedItem()).charAt(0);
						values[2] = ((String)third.getSelectedItem()).charAt(0);
						
						try {
							toServer.writeObject(Requests.Secret);
							toServer.writeObject(values);
							
							Requests r = (Requests)fromServer.readObject();
							switch(r)
							{
							case WrongSecret:
								JOptionPane.showMessageDialog(new JFrame(),
								"Wrong letters selected!",
								"Error",
								JOptionPane.WARNING_MESSAGE);
								break;
							case LogInValid:
								CustomerInformation info = (CustomerInformation)fromServer.readObject();
								double balance = (double)fromServer.readObject();
								JFrame normalWindow = new SelectionView(toServer, fromServer, info, balance);
								normalWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								normalWindow.setVisible(true);
								dispose();
								break;
							}
						} catch (IOException | ClassNotFoundException e1) {
							System.exit(1);
						}
						
					}
			
				});
	}
	
	
	

}
