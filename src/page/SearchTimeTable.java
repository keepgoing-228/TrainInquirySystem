package page;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilCalendarModel;
public class SearchTimeTable extends JFrame implements ActionListener, TrainInquirySystem {
	
	private JDatePicker datePicker = new JDatePicker(new UtilCalendarModel());
	private JComboBox<String> directionBox = new JComboBox<String>();
	private JLabel errorMessage = new JLabel();

	public SearchTimeTable() {
		this.setTitle("Train Inquiry");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(TrainInquirySystem.WIDTH, TrainInquirySystem.HEIGHT);
		this.setLayout(new BorderLayout());
		
		//Header on the top of the page. Maybe a menu bar is better.
		this.add(new Header(), BorderLayout.NORTH);
		
		//Panel use to locate query box to the middle.
		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridBagLayout());
		
		//Query box for entering information in the center.
		JPanel queryBox = new JPanel();
		queryBox.setLayout(new GridBagLayout());
		GridBagConstraints gridBag;
		
		//Border
		Border blackLine = BorderFactory.createLineBorder(Color.black);
		
		//Choose date
		//Reference: https://github.com/JDatePicker/JDatePicker
		JLabel datePickerLabel = new JLabel("Choose departure date: ");
		datePickerLabel.setOpaque(true);
		datePickerLabel.setBackground(Color.lightGray);
		datePickerLabel.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(datePickerLabel, gridBag);
		
		datePicker.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(datePicker, gridBag);
		
		JLabel directionLabel = new JLabel("Choose direction: ");
		directionLabel.setOpaque(true);
		directionLabel.setBackground(Color.LIGHT_GRAY);
		directionLabel.setBorder(blackLine);
		gridBag.gridx = 0;
		gridBag.gridy++;
		queryBox.add(directionLabel, gridBag);
		
		directionBox.addItem("To North");
		directionBox.addItem("To South");
		gridBag.gridx = 1;
		queryBox.add(directionBox, gridBag);
		
		gridBag.gridx = 0;
		gridBag.gridy++;
		queryBox.add(this.errorMessage, gridBag);
		
		gridBag = new GridBagConstraints();
		gridBag.insets = new Insets(0, 50, 0, 0);
		gridBag.anchor = GridBagConstraints.NORTHWEST;
		positionPanel.add(new JLabel("Time Table Searching"), gridBag);
		
		gridBag = new GridBagConstraints();
		gridBag.gridy = 1;
		gridBag.insets = new Insets(0, 50, 0, 100);
		positionPanel.add(queryBox, gridBag);
		
		this.add(positionPanel, BorderLayout.CENTER);
		
		JButton search = new JButton("Search");
		search.addActionListener(this);
		this.add(search, BorderLayout.SOUTH);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			Calendar departureDate = (Calendar) datePicker.getModel().getValue();
			if (departureDate == null) {
				throw new TrainQueryException(TrainQueryException.exceptionType.EMPTY_VALUE);
			}
			
			int direction;
			String directionString = String.format("%s", this.directionBox.getSelectedObjects());
			if (directionString.equals("To North")) {
				direction = 1;
			} else {
				direction = 0;
			}
			test.timeTablePage = new TimeTable((Calendar) datePicker.getModel().getValue(), direction);
			test.timeTablePage.setVisible(true);
			this.setVisible(false);
		} catch(TrainQueryException e2) {
			errorMessage.setText(e2.getMessage());
		}

		

	}
}