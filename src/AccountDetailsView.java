import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AccountDetailsView extends JFrame{

	private CustomerInformation info;
	
	
	public AccountDetailsView(CustomerInformation info)
	{
		super("Customer Details");
		this.info = info;
		
		JPanel panel = new JPanel(new GridLayout(10, 1, 5, 5));
		
		panel.add(new JLabel(String.valueOf(info.getTitle() + " "
				+ info.getFirstName() + " " + info.getSurname())));
		
		panel.add(new JLabel("Account ID: " + info.getId()));
		
		panel.add(new JLabel("---------_Address_---------"));
		panel.add(new JLabel(info.getStreetNr() + " " +
				info.getStreet()));
		
		panel.add(new JLabel(info.getPostCode()));
		panel.add(new JLabel(info.getCity() ));
		
		panel.add(new JLabel("---------Contacts---------"));
		panel.add(new JLabel(info.getEmail()));
		panel.add(new JLabel(info.getMainTel()));
		
		JButton done = new JButton("Done");
		done.addActionListener(e -> dispose());
		panel.add(done);
		
		add(panel);
		pack();
		
		
	}
}
