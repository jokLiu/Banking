package bank.views.selectionViews;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import bank.utilities.CustomerInformation;

/**
 * The Class AccountDetailsView.
 */
public class AccountDetailsView extends JFrame{

	/** The info. */
	private CustomerInformation info;
	
	
	/**
	 * Instantiates a new account details view.
	 *
	 * @param info the info
	 */
	public AccountDetailsView(CustomerInformation info)
	{
		super("Customer Details");
		this.info = info;
		
		JPanel panel = new JPanel(new GridLayout(7, 2, 8, 8));
		
		
		panel.add(new JLabel("Full name: "));
		addComponent(panel, new JTextField(String.valueOf(info.getTitle() + " "
				+ info.getFirstName() + " " + info.getSurname())));
		
		
		panel.add(new JLabel("Account ID: " ));
		addComponent(panel, new JTextField(String.valueOf(info.getId())));
		
		panel.add(new JLabel("Address: "));
		addComponent(panel,new JTextField(info.getStreetNr() + " " +
				info.getStreet()));
		
		
		panel.add(new JLabel("Post Code: "));
		addComponent(panel, new JTextField(info.getPostCode()));
		
		panel.add(new JLabel("City: "));
		addComponent(panel, new JTextField(info.getCity() ));
		
		panel.add(new JLabel("Email: "));
		addComponent(panel, new JTextField(info.getEmail()));

		panel.add(new JLabel("Main tel nr: "));
		addComponent(panel, new JTextField(info.getMainTel()));
		
		JButton done = new JButton("Done");
		done.addActionListener(e -> dispose());
		
		add(panel);
		add(done, BorderLayout.SOUTH);
		pack();
		
	}
	
	/**
	 * Adds the component to the  panel
	 *
	 * @param mainPanel the main panel
	 * @param field the field
	 */
	private void addComponent(JPanel mainPanel, JTextField field) {
		field.setEnabled(false);
		field.setDisabledTextColor(Color.BLACK);
		mainPanel.add(field);
	}
}
