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
public class TicketQuery extends JFrame implements ActionListener, TrainInquirySystem {

	private JComboBox<String> startStation = SimpleQuery.stationComboBoxFactory();
	private JComboBox<String> endStation = SimpleQuery.stationComboBoxFactory();
	private JTextField IDInputForWithoutCode = new JTextField(10);
	private JDatePicker datePicker = new JDatePicker(new UtilCalendarModel());
	private JTextField trainNoInput = new JTextField(10);
	private JTextField codeInput = new JTextField(10);
	private JLabel errorMessage = new JLabel();
	
	private JTextField IDInputWithCode = new JTextField(10);
	
	public TicketQuery() {
		this.setTitle("Train Inquiry");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(TrainInquirySystem.WIDTH, TrainInquirySystem.HEIGHT);
		this.setLayout(new BorderLayout());
		
		//Header on the top of the page. Maybe a menu bar is better.
		this.add(new Header(), BorderLayout.NORTH);
		this.add(new JLabel("simple Inquiry"));
		
		//Panel use to locate query box to the middle.
		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBag;

		//Query box for entering information in the center.
		JPanel queryWithCodeBox = this.queryWithCodeBoxFactory();
		JPanel queryWithoutCodeBox = this.queryWithoutCodeBoxFactory();
		
		gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.insets = new Insets(0, 50, 0, 0);
		gridBag.anchor = GridBagConstraints.NORTHWEST;
		positionPanel.add(new JLabel("With code"), gridBag);
		
		gridBag.gridy = 1;
		gridBag.insets = new Insets(0, 50, 0, 100);
		positionPanel.add(queryWithCodeBox, gridBag);
		
		gridBag.gridy = 2;
		positionPanel.add(new JLabel("Without code"), gridBag);
		
		gridBag.gridy = 3;
		positionPanel.add(queryWithoutCodeBox, gridBag);
		
		gridBag.gridy = 4;
		positionPanel.add(errorMessage, gridBag);
		
		this.add(positionPanel, BorderLayout.CENTER);
		
		JButton search = new JButton("Search");
		search.addActionListener(this);

		this.add(search, BorderLayout.SOUTH);
		
	}
	
	private JPanel queryWithoutCodeBoxFactory() {
		JPanel queryBox = new JPanel();
		queryBox.setLayout(new GridBagLayout());
		GridBagConstraints gridBag;
		
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
		startAndEndStatoin.add(this.startStation);
		JLabel endStationLabel = new JLabel("Ending Station:");
		startAndEndStatoin.add(endStationLabel);
		startAndEndStatoin.add(this.endStation);
		startAndEndStatoin.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(startAndEndStatoin, gridBag);
		
		//Enter ID
		JLabel IDLabel = new JLabel("ID");
		IDLabel.setOpaque(true);
		IDLabel.setBackground(Color.lightGray);
		IDLabel.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(IDLabel, gridBag);
		
		IDInputForWithoutCode.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(IDInputForWithoutCode, gridBag);
		
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
		
		this.datePicker.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 1;
		gridBag.gridy = 2;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(this.datePicker, gridBag);
		
		//Enter trainNo
		JLabel trainNoLabel = new JLabel("TrianNo");
		trainNoLabel.setOpaque(true);
		trainNoLabel.setBackground(Color.lightGray);
		trainNoLabel.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 3;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(trainNoLabel, gridBag);
		
		this.trainNoInput.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 1;
		gridBag.gridy = 3;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(this.trainNoInput, gridBag);
		
		return queryBox;
	}

	private JPanel queryWithCodeBoxFactory() {
		JPanel queryBox = new JPanel();
		queryBox.setLayout(new GridBagLayout());
		GridBagConstraints gridBag;
		
		//Border
		Border blackLine = BorderFactory.createLineBorder(Color.black);
		
		//Choose ticket type
		JLabel IDLabel = new JLabel("ID");
		IDLabel.setOpaque(true);
		IDLabel.setBackground(Color.lightGray);
		IDLabel.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(IDLabel, gridBag);
		
		this.IDInputWithCode.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 1;
		gridBag.gridy = 0;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(this.IDInputWithCode, gridBag);
		
		//Enter number of tickets
		JLabel codeLabel = new JLabel("Booking code");
		codeLabel.setOpaque(true);
		codeLabel.setBackground(Color.lightGray);
		codeLabel.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 1;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(codeLabel, gridBag);
		
		codeInput.setBorder(blackLine);
		gridBag = new GridBagConstraints();
		gridBag.gridx = 1;
		gridBag.gridy = 1;
		gridBag.fill = GridBagConstraints.BOTH;
		gridBag.anchor = GridBagConstraints.WEST;
		queryBox.add(codeInput, gridBag);
		
		return queryBox;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String ID = this.IDInputWithCode.getText();
		String code = this.codeInput.getText();
		try {
			ArrayList<String> result;
			if (this.isLegalInput(ID, code)) {
				result = this.searchTicket(code, ID);
			} else {
				ID = this.IDInputForWithoutCode.getText();
				if (ID.equals("")) {
					throw new TrainQueryException(TrainQueryException.exceptionType.WRONG_ID_LENGTH);
				}

				int startStation = SimpleQuery
						.getStationID(String.format("%s", this.startStation.getSelectedObjects()));
				int endStation = SimpleQuery.getStationID(String.format("%s", this.endStation.getSelectedObjects()));
				if (startStation == endStation) {
					throw new TrainQueryException(TrainQueryException.exceptionType.SAME_START_END_STATION);
				}
				
				Calendar departureDate = null;
				try {
					departureDate = (Calendar) this.datePicker.getModel().getValue();
				} catch (Exception noWork) {
					throw new TrainQueryException(TrainQueryException.exceptionType.EMPTY_VALUE);
				}
				
				int trainNo = 0;
				try {
					trainNo = Integer.parseInt(this.trainNoInput.getText());
				} catch (Exception noWork) {
					System.out.println(this.trainNoInput.getText());
					System.out.println(noWork.getMessage());
					throw new TrainQueryException(TrainQueryException.exceptionType.ILLEGAL_TRAINNO);
				}
				result = this.searchTicket(ID, trainNo, startStation, endStation, departureDate);
			}
			
			TicketInfo info = new TicketInfo(result);
			info.setVisible(true);
		} catch (TrainQueryException e2) {
			this.errorMessage.setText(e2.getMessage());
		}
	}

	private ArrayList<String> searchTicket(String ID, int trainNo, int startStation, int endStation,
			Calendar departureDate) throws TrainQueryException {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
			           "root", test.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(String.format("select code, discountType, car, r, position "
					+ "from ticket natural join booking "
					+ "where payerID = '%s' and trainNo = %d and startStation = %d and endStation = %d and departureDay = '%d/%d/%d';"
					, ID, trainNo, startStation, endStation, departureDate.get(Calendar.YEAR), departureDate.get(Calendar.MONTH) + 1, departureDate.get(Calendar.DATE)));
			if (!result.next()) {
				throw new TrainQueryException(TrainQueryException.exceptionType.WRONG_TICKET_INFO);
			} else {
				ArrayList<String> ticketInfo = new ArrayList<String>();  //0~8: code, trainNo, departureDate, discountType, startStation, endStation, departureTime, arriveTime, travelTime 9~: seat Info
				ticketInfo.add(result.getString("code"));
				ticketInfo.add(String.valueOf(trainNo));
				ticketInfo.add(String.format("%d/%d/%d", departureDate.get(Calendar.YEAR), departureDate.get(Calendar.MONTH) + 1, departureDate.get(Calendar.DATE)));
				ticketInfo.add(result.getString("discountType"));
				ticketInfo.add(SimpleQuery.getStationName(startStation));
				ticketInfo.add(SimpleQuery.getStationName(endStation));
				ticketInfo.add(SimpleQuery.getDepartureTime(trainNo, startStation));
				ticketInfo.add(SimpleQuery.getDepartureTime(trainNo, endStation));
				ticketInfo.add(SimpleQuery.getTravelTime(ticketInfo.get(6), ticketInfo.get(7)));
				do {
					ticketInfo.add(String.format("%s%s%s", result.getString("car"), result.getString("r"), result.getString("position")));
				} while(result.next());
				
				return ticketInfo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private ArrayList<String> searchTicket(String code, String ID) throws TrainQueryException {

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
			           "root", test.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(String.format("select trainNo, departureDay, discountType, startStation, endStation, car, r, position "
					+ "from booking natural join ticket "
					+ "where payerID = '%s' and code = '%s';", ID, code));
			if (!result.next()) {
				throw new TrainQueryException(TrainQueryException.exceptionType.WRONG_TICKET_INFO);
			} else {
				ArrayList<String> ticketInfo = new ArrayList<String>();  //0~8: code, trainNo, departureDate, discountType, startStation, endStation, departureTime, arriveTime, travelTime 9~: seat Info
				ticketInfo.add(code);
				ticketInfo.add(result.getString(1));
				ticketInfo.add(result.getString(2));
				ticketInfo.add(result.getString(3));
				ticketInfo.add(SimpleQuery.getStationName(result.getInt(4)));
				ticketInfo.add(SimpleQuery.getStationName(result.getInt(5)));
				ticketInfo.add(SimpleQuery.getDepartureTime(result.getInt(1), result.getInt(4)));
				ticketInfo.add(SimpleQuery.getDepartureTime(result.getInt(1), result.getInt(5)));
				ticketInfo.add(SimpleQuery.getTravelTime(ticketInfo.get(6), ticketInfo.get(7)));
				do {
					ticketInfo.add(String.format("%s%s%s", result.getString("car"), result.getString("r"), result.getString("position")));
				} while(result.next());
				result.close();
				
				return ticketInfo;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private boolean isLegalInput(String ID, String code) {
		
		if (ID.length() == 10 && code.length() == 10) {
			return true;
		} else {
			return false;
		}
	}
}