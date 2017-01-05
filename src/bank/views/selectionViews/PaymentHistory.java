package bank.views.selectionViews;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import bank.utilities.Transaction;

public class PaymentHistory extends JFrame {
	
	private ArrayList<Transaction> deposits;
	private ArrayList<Transaction> transfers;
	
	
	public PaymentHistory(ArrayList<Transaction> deposits,ArrayList<Transaction> transfers)
	{
		super("Transaction History");
		
		this.deposits = deposits;
		this.transfers = transfers;
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		String[] columnNames = {"Sender", "Recipient", "Description", "Date", "Amount"};
		
		String[][] info = new String[deposits.size() + transfers.size()][5];
		System.arraycopy(getDeposits(deposits), 0, info, 0, deposits.size());
		System.arraycopy(getDeposits(transfers), 0, info, deposits.size(), transfers.size());
		System.out.println(info.length);
		
		
		DefaultTableModel model = new DefaultTableModel(info, columnNames);
		JTable table = new JTable(model);
		table.setEnabled(false);
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		
		JButton exit = new JButton("Exit");
		exit.addActionListener(e -> dispose());
		panel.add(exit, BorderLayout.SOUTH);
		
		
		add(panel);
		pack();
		
		
	}
	
	private String[][] getDeposits(ArrayList<Transaction> data)
	{
		String[][] info = new String[data.size()][5];
		int count = 0;
		for(Transaction t: data)
		{
			info[count][0] = String.valueOf(t.getCustID());
			info[count][1] = String.valueOf(t.getToID());
			info[count][2] = t.getDesc();
			info[count][3] = String.valueOf(t.getTimeStamp());
			info[count][4] = String.valueOf(t.getAmount());
			count++;
		}
		return info;
	}
	
	
	

}
