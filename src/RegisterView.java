import java.awt.EventQueue;
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
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterView extends JFrame {
	
	private ObjectOutputStream toServer;
	private ObjectInputStream fromServer; 
	private JComboBox<Titles> title;
	private JTextField fName;
	private JTextField surname;
	private JTextField street;
	private JTextField stNr;
	private JTextField city;
	private JTextField postCode;
	private JTextField email;
	private JTextField mainTel;
	private JTextField tel2;
	private JTextField tel3;
	private JTextField usr;
	private JPasswordField psw;
	private JPasswordField secret;
	private JComboBox<CardType> type;
	private JButton submit;
	private JButton exit;
	
	public RegisterView(ObjectOutputStream toServer, ObjectInputStream fromServer)
	{
		super("Registration");
		
		this.toServer = toServer;
		this.fromServer = fromServer;
		JPanel panel = new JPanel(new GridLayout(16,2,2,2));
		
		
		//title row
		
		panel.add(new JLabel("Select title: "));
		title = new JComboBox<>();
		for(Titles t : Titles.values())
		{
			title.addItem(t);
		}
		panel.add(title);
		
		//first Name
		panel.add(new JLabel("Insert first name: "));
		fName = new JTextField();
		panel.add(fName);
		
		//last name
		panel.add(new JLabel("Insert last name: "));
		surname = new JTextField();
		panel.add(surname);
		
		//address
		//street
		panel.add(new JLabel("Insert street: "));
		street = new JTextField();
		panel.add(street);
		
//		street nr
		panel.add(new JLabel("Insert house nr: "));
		stNr = new JTextField();
		panel.add(stNr);
		
//		city
		panel.add(new JLabel("Insert city: "));
		city = new JTextField();
		panel.add(city);
		
//		post code
		panel.add(new JLabel("Insert post code: "));
		postCode = new JTextField();
		panel.add(postCode);
		
//		email
		panel.add(new JLabel("Insert email: "));
		email = new JTextField();
		panel.add(email);
		
//		main tel numb
		panel.add(new JLabel("Insert main tel nr: "));
		mainTel = new JTextField();
		panel.add(mainTel);
		
//		spare tel nrs
		panel.add(new JLabel("Insert home tel nr: "));
		tel2 = new JTextField();
		panel.add(tel2);
		
//		spare tel nr
		panel.add(new JLabel("Insert another tel nr: "));
		tel3 = new JTextField();
		panel.add(tel3);
		
//		username
		panel.add(new JLabel("Insert user name: "));
		usr = new JTextField();
		panel.add(usr);
		
//		password
		panel.add(new JLabel("Insert password: "));
		psw = new JPasswordField();
		panel.add(psw);
		
//		secret word
		panel.add(new JLabel("Insert secret word: "));
		secret = new JPasswordField();
		panel.add(secret);
		
//		card type
		panel.add(new JLabel("Insert card type: "));
		type = new JComboBox<>();
		for(CardType t : CardType.values())
		{
			type.addItem(t);
		}
		panel.add(type);
		
		
//		submit
		submit = new JButton("Submit");
		submitForm();
		panel.add(submit);
		
		
//		exit
		exit = new JButton("Exit");
		exit.addActionListener(e -> System.exit(1));
		panel.add(exit);
		
		add(panel);
		pack();
	}
	
	
	
	private void submitForm()
	{
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				CustomerInformation info = new CustomerInformation();
				info.setTitle((Titles)title.getSelectedItem());
				info.setFirstName(fName.getText());
				info.setSurname(surname.getText());
				info.setStreet(street.getText());
				info.setStreetNr(Integer.valueOf(stNr.getText()));
				info.setCity(city.getText());
				info.setPostCode(postCode.getText());
				info.setEmail(email.getText());
				info.setMainTel(mainTel.getText());
				info.setTelNr2(tel2.getText());
				info.setTelNr3(tel3.getText());
				info.setUsername(usr.getText());
				info.setPassword(String.valueOf(psw.getPassword()));
				info.setSecretWord(String.valueOf(secret.getPassword()).toLowerCase());
				info.setType((CardType)type.getSelectedItem());
				try {
					toServer.writeObject(Requests.Register);
					toServer.writeObject(info);
					Requests r = (Requests) fromServer.readObject();
					
					switch(r)
					{
						case RegisterUnsuccessful:
							//TODO register unsuccessful window
						case RegisterSuccessful:
							//TODO register successful window
							EventQueue.invokeLater(() -> {
								JFrame frame = new LogInView(toServer, fromServer);
								frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								frame.setVisible(true);
							});
							dispose();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					
					e1.printStackTrace();
				}
			}
			
		});
	}
	
	

}
