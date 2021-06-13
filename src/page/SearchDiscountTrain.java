package page;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
public class SearchDiscountTrain extends JFrame implements ActionListener, TrainInquirySystem {

	private JComboBox<String> startStation, endStation, departureTimeBox, arriveTimeBox;
	private JDatePicker datePicker = new JDatePicker(new UtilCalendarModel());
	private JLabel errorMessage = new JLabel();
	
	public SearchDiscountTrain() {
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
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(startAndEndStationLabel, c);		
		
		
		JLabel startStationLabel = new JLabel("Starting Station:");
		startAndEndStatoin.add(startStationLabel);
		startStation = SimpleQuery.stationComboBoxFactory();
		startAndEndStatoin.add(startStation);
		JLabel endStationLabel = new JLabel("Ending Station:");
		startAndEndStatoin.add(endStationLabel);
		endStation = SimpleQuery.stationComboBoxFactory();
	
		startAndEndStatoin.add(endStation);
		startAndEndStatoin.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(startAndEndStatoin, c);
		
	//	Choose ticket type
		JPanel timePanel = new JPanel();
		JLabel timeLabel = new JLabel("Departure/Arrive time");
		timeLabel.setOpaque(true);
		timeLabel.setBackground(Color.lightGray);
		timeLabel.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(timeLabel, c);		
		JLabel departureTimeLabel = new JLabel("Departure Time: ");
		timePanel.add(departureTimeLabel);
		departureTimeBox = timeComboBoxFactory();

		timePanel.add(departureTimeBox);
		JLabel arriveTimeLabel = new JLabel("Arrive Time: ");
		timePanel.add(arriveTimeLabel);
		arriveTimeBox = timeComboBoxFactory();
		timePanel.add(arriveTimeBox);
		timePanel.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(timePanel, c);
		//Choose date
		//Reference: https://github.com/JDatePicker/JDatePicker
		JLabel datePickerLabel = new JLabel("Choose departure date: ");
		datePickerLabel.setOpaque(true);
		datePickerLabel.setBackground(Color.lightGray);
		datePickerLabel.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(datePickerLabel, c);
		
		datePicker.setBorder(blackLine);
		c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;
		c.anchor = GridBagConstraints.WEST;
		queryBox.add(datePicker, c);
		
		c = new GridBagConstraints();
		c.insets = new Insets(0, 50, 0, 0);
		c.anchor = GridBagConstraints.NORTHWEST;
		positionPanel.add(new JLabel("Searching Discount Train"), c);
		
		c = new GridBagConstraints();
		c.gridy = 1;
		c.insets = new Insets(0, 50, 0, 100);
		positionPanel.add(queryBox, c);
		
		c = new GridBagConstraints();
		c.gridy = 3;
		positionPanel.add(this.errorMessage, c);
		
		this.add(positionPanel, BorderLayout.CENTER);
		
		JButton search = new JButton("Search");
		search.addActionListener(this);
		this.add(search, BorderLayout.SOUTH);
		
	}
	
	private JComboBox<String> timeComboBoxFactory() {
		JComboBox<String> box = new JComboBox<String>();
		box.addItem("00:00");
		box.addItem("01:00");
		box.addItem("02:00");
		box.addItem("03:00");
		box.addItem("04:00");
		box.addItem("05:00");
		box.addItem("06:00");
		box.addItem("07:00");
		box.addItem("08:00");
		box.addItem("09:00");
		box.addItem("10:00");
		box.addItem("11:00");
		box.addItem("12:00");
		box.addItem("13:00");
		box.addItem("14:00");
		box.addItem("15:00");
		box.addItem("16:00");
		box.addItem("17:00");
		box.addItem("18:00");
		box.addItem("19:00");
		box.addItem("20:00");
		box.addItem("21:00");
		box.addItem("22:00");
		box.addItem("23:00");
		return box;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		try {
			
			Calendar departureDate = null;
			try {
				departureDate = (Calendar) datePicker.getModel().getValue();
			} catch(Exception noWork) {
				throw new TrainQueryException(TrainQueryException.exceptionType.ILLEGAL_DEPARTURE_DATE);
			}
			
			if (!isLegalDepartureDate(departureDate)) {
				throw new TrainQueryException(TrainQueryException.exceptionType.ILLEGAL_DEPARTURE_DATE);
			}
			
			String departureTime = String.format("%s", this.departureTimeBox.getSelectedObjects());
			String arriveTime = String.format("%s", this.arriveTimeBox.getSelectedObjects());
			if (!isLegalTime(departureTime, arriveTime)) {
				throw new TrainQueryException(TrainQueryException.exceptionType.ILLEGAL_TIME);
			}
			
			int startStation = SimpleQuery.getStationID(String.format("%s", this.startStation.getSelectedObjects()));
			int endStation = SimpleQuery.getStationID(String.format("%s", this.endStation.getSelectedObjects()));
			if (startStation == endStation) {
				throw new TrainQueryException(TrainQueryException.exceptionType.SAME_START_END_STATION);
			}
			
			ArrayList<Integer> train = this.searchDiscountTrain(departureDate, departureTime, arriveTime, startStation, endStation);
			if (train != null) {
				test.discountTrainInfoPage = new DiscountTrainInfo(train, startStation, endStation, departureDate);
				test.discountTrainInfoPage.setVisible(true);
				this.setVisible(false);
			} else {
				this.errorMessage.setText("No train has been found!");
			}
			
		} catch(TrainQueryException e2) {
			this.errorMessage.setText(e2.getMessage());;
		}
	}
	
	private ArrayList<Integer> searchDiscountTrain(Calendar departureDate, String departureTime, String arriveTime, int startStation, int endStation) {
		
		int direction = 0;
		if (startStation < endStation) {
			direction = 0;
		} else if (startStation > endStation) {
			direction = 1;
		}
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
			           "root", test.PASSWORD);
			Statement stmt = conn.createStatement();
			String query = String.format(""
					+ "select trainNo \n"
					+ "from train natural join serviceday \n"
					+ "where direction = %d and day = '%s' \n"
					+ "and trainNo in (select S.trainNo from stop as S join stop as E on S.trainNo = E.trainNo where S.ID = %d and E.ID = %d and S.departureTime < '%s' and E.departureTime < '%s') \n"
					+ "and (trainNo in (select trainNo from universityDiscount where %s <> '100' ) \n"
					+ "or trainNo in (select trainNo from earlyDiscount where serviceday = '%s' group by trainNo having sum(tickets) > (select count(*) from ticket where trainNo = earlyDiscount.trainNo and discountType = 'Early Discount' and departureDay = '%s/%s/%s')));",
					direction, SimpleQuery.getDay(departureDate), startStation, endStation, departureTime, arriveTime, SimpleQuery.getDay(departureDate), SimpleQuery.getDay(departureDate), departureDate.get(Calendar.YEAR), departureDate.get(Calendar.MONTH) + 1, departureDate.get(Calendar.DATE));
			ResultSet result = stmt.executeQuery(query);
			
			ArrayList<Integer> train = new ArrayList<Integer>();
			if (!result.next()) {
				return null;
			} else {
				do {
					train.add(result.getInt(1));
				} while(result.next());
				return train;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private boolean isLegalTime(String departureTime, String arriveTime) {

		int departure = Integer.parseInt(departureTime.substring(0, 2));
		int arrive = Integer.parseInt(arriveTime.substring(0, 2));
		if (arrive <= departure) {
			return false;
		}
		return true;
	}

	
	private boolean isLegalDepartureDate(Calendar departureDate) {
		try {
			Calendar after = Calendar.getInstance();
			Calendar before = Calendar.getInstance();
			before.add(Calendar.DATE, 28);
			if (departureDate.after(after) && departureDate.before(before)) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}
}