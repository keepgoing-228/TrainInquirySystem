import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

import org.jdatepicker.JDatePicker;

public class TrainInquiry extends JFrame implements ActionListener, TrainInquirySystem {
	
	public static void main(String[] args) {

		TrainInquiry aPage = new TrainInquiry();
		aPage.setVisible(true);
	}

	public TrainInquiry() {
		this.setTitle("Train Inquiry");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(TrainInquirySystem.WIDTH, TrainInquirySystem.HEIGHT);
		this.setLayout(new BorderLayout());
		
		//Header on the top of the page. Maybe a menu bar is better.
		this.add(new Header(), BorderLayout.NORTH);
		this.add(new JLabel("Simple Inquiry"));
		
		//Panel use to locate query box to the middle.
		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridBagLayout());
		
		//Query box for entering information in the center.
		JPanel queryBox = new JPanel();
		queryBox.setLayout(new GridBagLayout());
		GridBagConstraints c;
		
		//Border
		Border blackLine = BorderFactory.createLineBorder(Color.black);
		
		//Choose starting and ending station
		JPanel startAndEndStatoin = new JPanel();
		JLabel startAndEndStationLabel = new JLabel("Starting and Ending Station: ");
		startAndEndStationLabel.setOpaque(true);
		startAndEndStationLabel.setBackground(Color.lightGray);
		startAndEndStationLabel.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(startAndEndStationLabel, c);		
		
		
		JLabel startStationLabel = new JLabel("Starting Station:");
		startAndEndStatoin.add(startStationLabel);
		JComboBox<String> startStation = new JComboBox<String>();
		startStation.addItem("Nan Gang");
		startStation.addItem("Taipei");
		startStation.addItem("Tao Yuan");
		startAndEndStatoin.add(startStation);
		JLabel endStationLabel = new JLabel("Ending Station:");
		startAndEndStatoin.add(endStationLabel);
		JComboBox<String> endStation = new JComboBox<String>();
		endStation.addItem("Nan Gang");
		endStation.addItem("Taipei");
		endStation.addItem("Tao Yuan");
		startAndEndStatoin.add(endStation);
		startAndEndStatoin.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(startAndEndStatoin, c);
		
		//Choose ticket type
		JLabel ticketTypeLabel = new JLabel("Choose Ticket Type: ");
		ticketTypeLabel.setOpaque(true);
		ticketTypeLabel.setBackground(Color.lightGray);
		ticketTypeLabel.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(ticketTypeLabel, c);
		
		JComboBox<String> ticketType = new JComboBox<String>();
		ticketType.addItem("Standard");
		ticketType.addItem("Business");
		ticketType.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(ticketType, c);
		
		//Choose date
		//Reference: https://github.com/JDatePicker/JDatePicker
		JLabel datePickerLabel = new JLabel("Choose deliver date: ");
		datePickerLabel.setOpaque(true);
		datePickerLabel.setBackground(Color.lightGray);
		datePickerLabel.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(datePickerLabel, c);
		
		JDatePicker datePicker = new JDatePicker();
		datePicker.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(datePicker, c);
		
		//Enter number of tickets
		JLabel numbersOfTicketLabel = new JLabel("Number of tickets");
		numbersOfTicketLabel.setOpaque(true);
		numbersOfTicketLabel.setBackground(Color.lightGray);
		numbersOfTicketLabel.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(numbersOfTicketLabel, c);
		
		JTextField numbersOfTicket = new JTextField(10);
		numbersOfTicket.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(numbersOfTicket, c);
		
		c = new GridBagConstraints();
		c.insets = new Insets(0, 50, 0, 0);
		c.anchor = GridBagConstraints.NORTHWEST;
		positionPanel.add(new JLabel("Simple Inquiry"), c);
		
		c = new GridBagConstraints();
		c.gridy = 1;
		c.insets = new Insets(0, 50, 0, 100);
		positionPanel.add(queryBox, c);
		
		this.add(positionPanel, BorderLayout.CENTER);
		
		JButton search = new JButton("Search");
		this.add(search, BorderLayout.SOUTH);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
