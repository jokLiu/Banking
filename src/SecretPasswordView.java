import java.awt.BorderLayout;
import java.awt.EventQueue;
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
import javax.swing.JPanel;

public class SecretPasswordView extends JFrame {
	
	private JComboBox<String> first;
	private JComboBox<String> second;
	private JComboBox<String> third;
	private JButton login;
	
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer; 
	
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
		panel.add(login, BorderLayout.AFTER_LAST_LINE);
		addListenerLogIn();
		
		add(panel);
		pack();
		
		
	}
	
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
								//TODO display GUI saying wrong
							case LogInValid:
								System.out.println("valid");
								//TODO open a normal window
								dispose();
							}
						} catch (IOException | ClassNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
			
				});
	}
	
	
	

}
