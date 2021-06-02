package Page;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.border.Border;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.SqlDateModel;
import org.jdatepicker.UtilCalendarModel;

import com.mysql.cj.protocol.Resultset;

import Page.TrainQueryException.exceptionType;

public class TrainInquiry extends JFrame implements ActionListener, TrainInquirySystem {

	JComboBox<String> startStation = stationComboBoxFactory();
	JComboBox<String> endStation = stationComboBoxFactory();
	JComboBox<String> ticketType = new JComboBox<String>();
	JDatePicker datePicker = new JDatePicker(new UtilCalendarModel());
	JTextField numbersOfTicket = new JTextField(10);
	GridBagConstraints gridBag;
	JPanel positionPanel = new JPanel();
	JLabel exceptionLabel = new JLabel();
	
	public TrainInquiry() {
		this.setTitle("Train Inquiry system");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(TrainInquirySystem.WIDTH, TrainInquirySystem.HEIGHT);
		this.setLayout(new BorderLayout());
		
		//Header on the top of the page. Maybe a menu bar is better.
		this.add(new Header(), BorderLayout.NORTH);
		this.add(new JLabel("Simple Inquiry"));
		
		//Panel use to locate query box to the middle.
		positionPanel.setLayout(new GridBagLayout());
		
		//Query box for entering information in the center.
		JPanel queryBox = new JPanel();
		queryBox.setLayout(new GridBagLayout());
		
		//Border
		Border blackLine = BorderFactory.createLineBorder(Color.black);
		
		//Choose starting and ending station
		JPanel startAndEndStatoin = new JPanel();
		JLabel startAndEndStationLabel = new JLabel("Starting and Ending Station: ");
		startAndEndStationLabel.setOpaque(true);
		startAndEndStationLabel.setBackground(Color.lightGray);
		startAndEndStationLabel.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(startAndEndStationLabel, gridBag);		
		
		
		JLabel startStationLabel = new JLabel("Starting Station:");
		startAndEndStatoin.add(startStationLabel);
		startAndEndStatoin.add(startStation);
		JLabel endStationLabel = new JLabel("Ending Station:");
		startAndEndStatoin.add(endStationLabel);
		startAndEndStatoin.add(endStation);
		startAndEndStatoin.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(startAndEndStatoin, gridBag);
		
		//Choose ticket type
		JLabel ticketTypeLabel = new JLabel("Choose Ticket Type: ");
		ticketTypeLabel.setOpaque(true);
		ticketTypeLabel.setBackground(Color.lightGray);
		ticketTypeLabel.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(ticketTypeLabel, gridBag);
		
		ticketType.addItem("Standard");
		ticketType.addItem("Business");
		ticketType.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(ticketType, gridBag);
		
		//Choose date
		//Reference: https://github.com/JDatePicker/JDatePicker
		JLabel datePickerLabel = new JLabel("Choose deliver date: ");
		datePickerLabel.setOpaque(true);
		datePickerLabel.setBackground(Color.lightGray);
		datePickerLabel.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 2;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(datePickerLabel, gridBag);
		
		datePicker.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 1;
		gridBag.gridy = 2;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(datePicker, gridBag);
		
		//Enter number of tickets
		JLabel numbersOfTicketLabel = new JLabel("Number of tickets");
		numbersOfTicketLabel.setOpaque(true);
		numbersOfTicketLabel.setBackground(Color.lightGray);
		numbersOfTicketLabel.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 3;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(numbersOfTicketLabel, gridBag);
		
		numbersOfTicket.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 1;
		gridBag.gridy = 3;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(numbersOfTicket, gridBag);
		
		gridBag = new GridBagConstraints();
		gridBag.insets = new Insets(0, 50, 0, 0);
		gridBag.anchor = GridBagConstraints.NORTHWEST;
		positionPanel.add(new JLabel("Simple Inquiry"), gridBag);
		
		gridBag = new GridBagConstraints();
		gridBag.gridy = 1;
		gridBag.insets = new Insets(0, 50, 0, 100);
		positionPanel.add(queryBox, gridBag);
		
		this.add(positionPanel, BorderLayout.CENTER);
		
		JButton search = new JButton("Search");
		search.addActionListener(this);
		gridBag.gridy = 2;
		positionPanel.add(search, gridBag);
		
		gridBag.gridy = 3;
		positionPanel.add(exceptionLabel, gridBag);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
			           "root", "Timecompressor1919810");
			int startStationID = searchStation(conn, this.startStation.getSelectedObjects());
			int endStationID = searchStation(conn, this.endStation.getSelectedObjects());
			int direction;
			if (startStationID < endStationID) {
				direction = 0;
			} else if (startStationID > endStationID) {
				direction = 1;
			} else {
				throw new TrainQueryException(TrainQueryException.exceptionType.SAME_START_END_STATION);
			}
			String ticketType = String.format("%s", this.ticketType.getSelectedObjects());
			int ticketTypeCount;
			if (ticketType.equals("Standard")) {
				ticketTypeCount = 919;
			} else {
				ticketTypeCount = 66;
			}
			Calendar departureDate = (Calendar) datePicker.getModel().getValue();
			Integer ticketCount = null;
			try {
				ticketCount = Integer.parseInt(numbersOfTicket.getText());
			} catch(Exception noWork) {}
			if (ticketCount == null || ticketCount <= 0 || ticketCount > 10) {
				throw new TrainQueryException(TrainQueryException.exceptionType.WRONG_NUMBERS_OF_TICKET);
			}
			
			String query = "select distinct trainNo\r\n"
					+ "from train natural join serviceday natural join stop\r\n"
					+ "where direction = %d and ID = %d and day = '%s' and trainNo not in\r\n"
					+ "(select trainNo \r\n"
					+ "from ticket natural join seat \r\n"
					+ "where type = '%s' and departureDay = '%s/%s/%s' and (endStation %c= %d or startStation %c= %d)\r\n"
					+ "group by trainNo, departureDay \r\n"
					+ "having count(position) + %d > %d)\r\n"
					+ "and trainNo in\r\n"
					+ "(select distinct A.trainNo\r\n"
					+ "from stop as A join stop as B on (A.trainNo = B.trainNo)\r\n"
					+ "where A.ID = %d and B.ID = %d)\r\n"
					+ "order by departureTime asc;";
			if (direction == 0) {
				query = String.format(query, direction, startStationID, test.getDay(departureDate), ticketType, departureDate.get(Calendar.YEAR), departureDate.get(Calendar.MONTH) + 1, departureDate.get(Calendar.DATE), '>', startStationID, '<', endStationID, ticketCount, ticketTypeCount, startStationID, endStationID);
			} else {
				query = String.format(query, direction, startStationID, test.getDay(departureDate), ticketType, departureDate.get(Calendar.YEAR), departureDate.get(Calendar.MONTH) + 1, departureDate.get(Calendar.DATE), '<', startStationID, '>', endStationID, ticketCount, ticketTypeCount, startStationID, endStationID);
			}
			//System.out.println(query);
			Statement stmt = conn.createStatement();
			ResultSet trainNo = stmt.executeQuery(query);
			ArrayList<String> trainNoList = new ArrayList<String>();
			if (!trainNo.next()) {
				throw new TrainQueryException(TrainQueryException.exceptionType.NO_ACCESSABLE_TICKET);
			}
			while (trainNo.next()) {
				trainNoList.add(trainNo.getString("TrainNo"));
			}
			trainNo.close();
			//System.out.println(trainNoList.toString());
			test.trainNoselectionPage = new TrainNoSelection(trainNoList, startStationID, endStationID, ticketType, departureDate, ticketCount);
			test.trainNoselectionPage.setVisible(true);
			this.setVisible(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
			this.dispose();
		} catch (TrainQueryException e2) {
			exceptionLabel.setText(e2.getMessage());
		}

	}
	
	private int searchStation(Connection conn, Object[] selectedStation) {
		try {
			Statement stmt = conn.createStatement();
			String searchStation = String.format("select ID from Station where enName = '%s'", selectedStation);
			ResultSet station = stmt.executeQuery(searchStation);
			station.next();
			int stationID = station.getInt("ID");
			station.close();
			return stationID;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.printf("Cannot find station %s.\n", selectedStation);
			return 0;
		}
	}

	private JComboBox<String> stationComboBoxFactory() {
		JComboBox<String> comboBox = new JComboBox<String>();
		String[] stationName = {"Nangang", "Taipei", "Banciao", "Taoyuan", "Hsinchu", "Miaoli", "Taichung", "Changhua", "Yunlin", "Chiayi", "Tainan", "Zuoying"};
		for (int i = 0; i < stationName.length; i++) {
			comboBox.addItem(stationName[i]);
		}
		return comboBox;
	}
	
}
