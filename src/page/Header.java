package page;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Header extends JPanel implements ActionListener{

	public Header() {
		super();
		this.setLayout(new FlowLayout());
		JButton simpleInquiry = new JButton("Ticket Inquiry");
		simpleInquiry.addActionListener(this);
		this.add(simpleInquiry);
		JButton checkTicket = new JButton("Check Ticket");
		checkTicket.addActionListener(this);
		this.add(checkTicket);
		JButton timeTable = new JButton("Time Table");
		timeTable.addActionListener(this);
		this.add(timeTable);
		JButton discountQuery = new JButton("Search Discount Train");
		discountQuery.addActionListener(this);
		this.add(discountQuery);
		JButton changeTicket = new JButton("Change or Cancel Ticket");
		changeTicket.addActionListener(this);
		this.add(changeTicket);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		test.disposeAllPage();
		
		switch (e.getActionCommand()) {
		case "Ticket Inquiry":{
			test.inquiryPage = new TrainInquiry();
			test.inquiryPage.setVisible(true);
			break;
		}
		case "Check Ticket":{
			test.ticketQueryPage = new TicketQuery();
			test.ticketQueryPage.setVisible(true);
			break;
		}
		case "Time Table":{
			test.searchTimeTablePage = new SearchTimeTable();
			test.searchTimeTablePage.setVisible(true);
			break;
		}
		case "Search Discount Train":{
			test.searchDiscountTrainPage = new SearchDiscountTrain();
			test.searchDiscountTrainPage.setVisible(true);
			break;
		}
		case "Change or Cancel Ticket":{
			test.ReviseMenuPage = new TrainReviseTicketMenu();
			test.ReviseMenuPage.setVisible(true);
			break;
		}
		}
		
	}
	
}
