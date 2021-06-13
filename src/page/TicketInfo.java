package page;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TicketInfo extends JFrame {

	public TicketInfo(ArrayList<String> ticketInfo) {
		this.setTitle("Ticket Info");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(350, 300);
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.anchor = GridBagConstraints.WEST;
		gridBag.insets = new Insets(0, 30, 0, 0);
		String[] label = {"Code: ", "TrainNo", "Departure Date: ", "Discount Type: ", "Start Station: ", "End Station: ", "Departure Time: ", "Arrive Time: ", "Travel Time: "};
		
		for (int i = 0; i < label.length; i++) {
			gridBag.gridx = 0;
			gridBag.gridy = i;
			this.add(new JLabel(label[i]), gridBag);
			
			gridBag.gridx = 1;
			gridBag.gridy = i;
			this.add(new JLabel(ticketInfo.get(i)), gridBag);
		}
		
		String seats = "";
		for (int i = label.length; i < ticketInfo.size(); i++) {
			seats = seats + ticketInfo.get(i) + ", ";
		}
		seats = seats.substring(0, seats.length() - 2);
		
		gridBag.gridx = 0;
		gridBag.gridy++;
		this.add(new JLabel("Seats: "), gridBag);
		
		gridBag.gridx = 1;
		this.add(new JLabel(seats), gridBag);
		
		//0~8: code, trainNo, departureDate, discountType, startStation, endStation, departureTime, arriveTime, travelTime 9~: seat Info
	}
	
}
