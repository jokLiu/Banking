package bank.views;

import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bank.utilities.CardType;
import bank.utilities.CustomerInformation;
import bank.utilities.Requests;
import bank.utilities.Titles;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterView.
 */
public class RegisterView extends JFrame {

	/** The to server. */
	private ObjectOutputStream toServer;
	
	/** The from server. */
	private ObjectInputStream fromServer;
	
	/** The title. */
	private JComboBox<Titles> title;
	
	/** The name. */
	private JTextField fName;
	
	/** The surname. */
	private JTextField surname;
	
	/** The street. */
	private JTextField street;
	
	/** The st nr. */
	private JTextField stNr;
	
	/** The city. */
	private JTextField city;
	
	/** The post code. */
	private JTextField postCode;
	
	/** The email. */
	private JTextField email;
	
	/** The main tel. */
	private JTextField mainTel;
	
	/** The tel2. */
	private JTextField tel2;
	
	/** The tel3. */
	private JTextField tel3;
	
	/** The usr. */
	private JTextField usr;
	
	/** The psw. */
	private JPasswordField psw;
	
	/** The secret. */
	private JPasswordField secret;
	
	/** The type. */
	private JComboBox<CardType> type;
	
	/** The submit. */
	private JButton submit;
	
	/** The exit. */
	private JButton exit;

	/**
	 * Instantiates a new register view.
	 *
	 * @param toServer the to server
	 * @param fromServer the from server
	 */
	public RegisterView(ObjectOutputStream toServer, ObjectInputStream fromServer) {
		super("Registration");

		this.toServer = toServer;
		this.fromServer = fromServer;
		JPanel panel = new JPanel(new GridLayout(16, 2, 2, 2));

		// title row

		panel.add(new JLabel("Select title: "));
		title = new JComboBox<>();
		for (Titles t : Titles.values()) {
			title.addItem(t);
		}
		panel.add(title);

		// first Name
		panel.add(new JLabel("Insert first name: "));
		fName = new JTextField();
		panel.add(fName);

		// last name
		panel.add(new JLabel("Insert last name: "));
		surname = new JTextField();
		panel.add(surname);

		// address
		// street
		panel.add(new JLabel("Insert street: "));
		street = new JTextField();
		panel.add(street);

		// street nr
		panel.add(new JLabel("Insert house nr: "));
		stNr = new JTextField();
		panel.add(stNr);

		// city
		panel.add(new JLabel("Insert city: "));
		city = new JTextField();
		panel.add(city);

		// post code
		panel.add(new JLabel("Insert post code: "));
		postCode = new JTextField();
		panel.add(postCode);

		// email
		panel.add(new JLabel("Insert email: "));
		email = new JTextField();
		panel.add(email);

		// main tel numb
		panel.add(new JLabel("Insert main tel nr: "));
		mainTel = new JTextField();
		panel.add(mainTel);

		// spare tel nrs
		panel.add(new JLabel("Insert home tel nr: "));
		tel2 = new JTextField();
		panel.add(tel2);

		// spare tel nr
		panel.add(new JLabel("Insert another tel nr: "));
		tel3 = new JTextField();
		panel.add(tel3);

		// username
		panel.add(new JLabel("Insert user name: "));
		usr = new JTextField();
		panel.add(usr);

		// password
		panel.add(new JLabel("Insert password: "));
		psw = new JPasswordField();
		panel.add(psw);

		// secret word
		panel.add(new JLabel("Insert secret word: "));
		secret = new JPasswordField();
		panel.add(secret);

		// card type
		panel.add(new JLabel("Insert card type: "));
		type = new JComboBox<>();
		for (CardType t : CardType.values()) {
			type.addItem(t);
		}
		panel.add(type);

		// submit
		submit = new JButton("Submit");
		submitForm();
		panel.add(submit);

		// exit
		exit = new JButton("Exit");
		exit.addActionListener(e -> System.exit(1));
		panel.add(exit);

		add(panel);
		pack();
	}

	/**
	 * Submit form.
	 */
	private void submitForm() {
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isBlank = false;

				CustomerInformation info = new CustomerInformation();
				info.setTitle((Titles) title.getSelectedItem());

				String firstName = fName.getText();
				check(firstName, fName, isBlank);
				info.setFirstName(firstName);

				String lastName = surname.getText();
				check(lastName, surname, isBlank);
				info.setSurname(lastName);

				String str = street.getText();
				check(str, street, isBlank);
				info.setStreet(str);

				String strNum = stNr.getText();
				check(strNum, stNr, isBlank);
				info.setStreetNr(strNum);

				String cityName = city.getText();
				check(cityName, city, isBlank);
				info.setCity(cityName);

				String pCode = postCode.getText();
				check(pCode, postCode, isBlank);
				info.setPostCode(pCode);

				String mail = email.getText();
				check(mail, email, isBlank);
				info.setEmail(mail);

				String telNr = mainTel.getText();
				check(telNr, mainTel, isBlank);
				info.setMainTel(telNr);

				String telNr2 = tel2.getText();
				if(telNr2.isEmpty()) telNr2 = telNr;
				info.setTelNr2(telNr2);

				String telNr3 = tel3.getText();
				if(telNr3.isEmpty()) telNr3 = telNr;
				info.setTelNr3(telNr3);

				String username = usr.getText();
				check(username, usr, isBlank);
				info.setUsername(username);

				String password = String.valueOf(psw.getPassword());
				
				boolean isStrong = isStrong(password);
				if (isStrong)
					info.setPassword(password);
				else
					errorWindow(
							"Password is not strong enough! \n Must be longer than 7 character \n Consist at least one upper case letter and a digit. ");

				String secretWord = String.valueOf(secret.getPassword()).toLowerCase();
				boolean isSecret = isGoodSecret(secretWord);
				if (isSecret)
					info.setSecretWord(secretWord);
				else
					errorWindow(
							"Security word is in the wrong format! \n Must be longer than 7 character \n Consist only letters and numbers. ");
				
				check(password, psw, isBlank);
				check(secretWord, secret, isBlank);
				
				info.setType((CardType) type.getSelectedItem());

				if (isStrong && isSecret && !isBlank) {
					try {
						toServer.writeObject(Requests.Register);
						toServer.writeObject(info);
						Requests r = (Requests) fromServer.readObject();

						switch (r) {
						case RegisterUnsuccessful:
							errorWindow("User name already exists!");
							break;
						case RegisterSuccessful:
							JOptionPane.showMessageDialog(new JFrame(), "Registration successful!");
							EventQueue.invokeLater(() -> {
								JFrame frame = new LogInView(toServer, fromServer);
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
			}

		});
	}
	
	/**
	 * Check if JText field is empty and set colour accordingly
	 *
	 * @param var String variable
	 * @param field the field
	 * @param isBlank the is blank
	 */
	private void check(String var, JTextField field, boolean isBlank)
	{
		if (var.isEmpty())
		{
			isBlank = true;
			setRed(field);
		}
		else
		{
			setWhite(field);					
		}
	}
	
	/**
	 * Sets the white color
	 *
	 * @param field the new white
	 */
	private void setWhite(JTextField field)
	{
		field.setForeground(Color.BLACK);
		field.setBackground(Color.WHITE);
	}
	
	/**
	 * Sets the red color
	 *
	 * @param field the new red
	 */
	private void setRed(JTextField field)
	{
		field.setForeground(Color.WHITE);
		field.setBackground(Color.RED);
	}

	/**
	 * Checks if password is strong
	 *
	 * @param password the password
	 * @return true, if is strong
	 */
	private boolean isStrong(String password) {
		boolean valid = true;

		valid = password.length() >= 8;
		valid = !password.toLowerCase().equals(password) && valid;
		valid = password.matches(".*\\d+.*") && valid;

		return valid;

	}

	/**
	 * Checks if is good secret.
	 *
	 * @param secretW the secret word
	 * @return true, if is good secret
	 */
	private boolean isGoodSecret(String secretW) {
		boolean valid = true;

		valid = secretW.length() >= 8;

		int count = 0;
		for (int i = 0; i < secretW.length(); i++) {
			char c = secretW.charAt(i);
			if ((c >= 'a' && c <= 'z') || (c >= '0' && c <= '9'))
				count++;
		}
		valid = valid && (count == secretW.length());
		return valid;
	}

	/**
	 * Error window.
	 *
	 * @param msg the msg
	 */
	private void errorWindow(String msg) {
		JOptionPane.showMessageDialog(new JFrame(), msg, "Error", JOptionPane.WARNING_MESSAGE);
	}
	
	
}
