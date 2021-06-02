package Page;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PayerInformation extends JFrame implements ActionListener, TrainInquirySystem {

	private static final int insetsBetweenColumn = 15;
	private String[] trainInfo;
	private Object[][] discount;
	private Calendar departureDate;
	private int startStationID, endStationID;
	private int ticketCount;
	private String ticketType;
	private int[] ticketPrice = new int[2];
	String payerID, phone;
	JTextField ID = new JTextField(10);
	JTextField phoneField = new JTextField(10);

	
	public PayerInformation(String[] trainInfo, Object[][] discount, Calendar departureDate, int startStationID, int endStationID, int ticketCount, String ticketType) {
		this.setTitle("Train Inquiry system");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(TrainInquirySystem.WIDTH, TrainInquirySystem.HEIGHT);
		this.setLayout(new BorderLayout());
		this.initialize(trainInfo, discount, departureDate, startStationID, endStationID, ticketCount, ticketType);
		
		this.add(new Header(), BorderLayout.NORTH);

		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.anchor = GridBagConstraints.WEST;
		gridBag.ipady = 50;
		
		JPanel detailPanel = this.detailPanelFactory();
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		positionPanel.add(detailPanel, gridBag);
		
		JPanel informationPanel = this.informationPanelFactory();
		gridBag.gridy = 1;
		positionPanel.add(informationPanel, gridBag);
		
		JPanel buttonPanel = this.buttonPanelFactory();
		gridBag.gridy = 2;
		gridBag.anchor = GridBagConstraints.CENTER;
		positionPanel.add(buttonPanel, gridBag);
		
		
		this.add(positionPanel, BorderLayout.CENTER);

	}

	private void initialize(String[] trainInfo, Object[][] discount, Calendar departureDate, int startStationID, int endStationID, int ticketCount, String ticketType) {
		this.trainInfo = trainInfo;
		this.discount = discount;
		this.departureDate = departureDate;
		this.startStationID = startStationID;
		this.endStationID = endStationID;
		this.ticketCount = ticketCount;
		this.ticketType = ticketType;
	}

	private JPanel detailPanelFactory() {
		JPanel detailPanel = new JPanel();
		detailPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.ipadx = insetsBetweenColumn;
		gridBag.fill = GridBagConstraints.BOTH;
		
		//Title
		gridBag.gridy = 0;
		gridBag.gridx = 0;
		detailPanel.add(new JLabel("Booking Details"), gridBag);
		
		//Departure Day
		gridBag.gridy = 1;
		gridBag.gridx = 0;
		JLabel departureLabel = new JLabel("Departure Day");
		departureLabel.setOpaque(true);
		departureLabel.setBackground(Color.LIGHT_GRAY);
		detailPanel.add(departureLabel, gridBag);
		gridBag.gridy = 2;
		detailPanel.add(new JLabel(String.format("%d/%d", this.departureDate.get(Calendar.MONTH) + 1, this.departureDate.get(Calendar.DATE))), gridBag);
	
		//TrainNo
		gridBag.gridy = 1;
		gridBag.gridx = 1;
		JLabel trainNoLabel = new JLabel("TrainNo");
		trainNoLabel.setOpaque(true);
		trainNoLabel.setBackground(Color.LIGHT_GRAY);
		detailPanel.add(trainNoLabel, gridBag);
		gridBag.gridy = 2;
		detailPanel.add(new JLabel(String.format(this.trainInfo[0])), gridBag);
		
		//Start Station
		gridBag.gridy = 1;
		gridBag.gridx = 2;
		JLabel startStationLabel = new JLabel("Start Station");
		startStationLabel.setOpaque(true);
		startStationLabel.setBackground(Color.LIGHT_GRAY);
		detailPanel.add(startStationLabel, gridBag);
		gridBag.gridy = 2;
		detailPanel.add(new JLabel(this.getStationName(startStationID)), gridBag);
		
		//End Station
		gridBag.gridy = 1;
		gridBag.gridx = 3;
		JLabel endStationLabel = new JLabel("End Station");
		endStationLabel.setOpaque(true);
		endStationLabel.setBackground(Color.LIGHT_GRAY);
		detailPanel.add(endStationLabel, gridBag);
		gridBag.gridy = 2;
		detailPanel.add(new JLabel(this.getStationName(endStationID)), gridBag);
		
		//Departure Time
		gridBag.gridy = 1;
		gridBag.gridx = 4;
		JLabel departureTimeLabel = new JLabel("Departure Time");
		departureTimeLabel.setOpaque(true);
		departureTimeLabel.setBackground(Color.LIGHT_GRAY);
		detailPanel.add(departureTimeLabel, gridBag);
		detailPanel.add(new JLabel("Departure Time"), gridBag);
		gridBag.gridy = 2;
		detailPanel.add(new JLabel(this.trainInfo[1]), gridBag);
		
		//Arrive Time
		gridBag.gridy = 1;
		gridBag.gridx = 5;
		JLabel arriveTimeLabel = new JLabel("Arrive Time");
		arriveTimeLabel.setOpaque(true);
		arriveTimeLabel.setBackground(Color.LIGHT_GRAY);
		detailPanel.add(arriveTimeLabel, gridBag);
		gridBag.gridy = 2;
		detailPanel.add(new JLabel(this.trainInfo[2]), gridBag);
		
		//Discount
		gridBag.gridy = 1;
		gridBag.gridx = 6;
		JLabel discountLabel = new JLabel("Discount");
		discountLabel.setOpaque(true);
		discountLabel.setBackground(Color.LIGHT_GRAY);
		detailPanel.add(discountLabel, gridBag);
		gridBag.gridy = 2;
		detailPanel.add(new JLabel(this.discountToString()), gridBag);
		
		//Price
		gridBag.gridy = 1;
		gridBag.gridx = 7;
		JLabel priceLabel = new JLabel("Price");
		priceLabel.setOpaque(true);
		priceLabel.setBackground(Color.LIGHT_GRAY);
		detailPanel.add(priceLabel, gridBag);
		gridBag.gridy = 2;
		detailPanel.add(new JLabel(this.countPrice()), gridBag);
		
		return detailPanel;
	}

	private String getStationName(int stationID) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
			           "root", "Timecompressor1919810");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(String.format("select enName from station where ID = %d;", stationID));
			result.next();
			String name = result.getString(1);
			result.close();
			return name;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String countPrice() {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
			           "root", "Timecompressor1919810");
			Statement stmt = conn.createStatement();
			ResultSet result;
			double price = 0;
			String query;
			if (this.discount == null) {
				query = String.format("select %sPrice from ticketPrice where startStation = %d and endStation = %d",
						this.ticketType, this.startStationID, this.endStationID);
				result = stmt.executeQuery(query);
				result.next();
				this.ticketPrice[0] = result.getInt(1);
				price = this.ticketCount * result.getDouble(1);
				result.close();
			} else if (this.discount[1][0] == null) {
				query = String.format("select s%s from ticketPrice where startStation = %d and endStation = %d",
						this.discount[0][0], this.startStationID, this.endStationID);
				result = stmt.executeQuery(query);
				result.next();
				this.ticketPrice[0] = result.getInt(1);
				price = this.ticketCount * result.getDouble(1);
				result.close();
			} else {
				for (int i = 0; i < 2; i++) {
					query = String.format("select s%s from ticketPrice where startStation = %d and endStation = %d",
							this.discount[i][0], this.startStationID, this.endStationID);
					result = stmt.executeQuery(query);
					result.next();
					this.ticketPrice[i] = result.getInt(1);
					price += (Double) this.discount[i][1] * result.getDouble(1);
					result.close();
				}
			}
			return String.format("%.2f", price);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String discountToString() {
		String result;
		
		if (this.discount == null) {
			result = String.format("No discount * %d", this.ticketCount);
		} else if (this.discount[1][0] == null) {
			result = String.format("%s%c * %d", this.discount[0][0], '%', this.discount[0][1]);
		} else {
			result = String.format("%s%c * %d, %s%c * %d", this.discount[0][0], '%', this.discount[0][1], this.discount[1][0], '%', this.discount[1][1]);
		}
		return result;
	}
	
	private JPanel informationPanelFactory() {
		
		JPanel informationPanel = new JPanel();
		informationPanel.setLayout(new GridBagLayout());
		GridBagConstraints gridBag = new GridBagConstraints();
		
		gridBag.anchor = GridBagConstraints.WEST;
		gridBag.gridy = 0;
		gridBag.gridx = 0;
		informationPanel.add(new JLabel("Payer Information"));
		
		//ID
		JPanel IDInputPanel = new JPanel();
		IDInputPanel.setLayout(new GridBagLayout());
		IDInputPanel.setOpaque(true);
		IDInputPanel.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints inputGrid = new GridBagConstraints();
		inputGrid.gridx = 0;
		inputGrid.gridy = 0;
		inputGrid.ipadx = insetsBetweenColumn;
		IDInputPanel.add(new JLabel("ID: "), inputGrid);
		inputGrid.gridx = 1;
		IDInputPanel.add(this.ID, inputGrid);
		gridBag.gridy = 1;
		informationPanel.add(IDInputPanel, gridBag);
		
		//Phone
		JPanel phoneInputPanel = new JPanel();
		phoneInputPanel.setLayout(new GridBagLayout());
		inputGrid = new GridBagConstraints();
		inputGrid.gridx = 0;
		inputGrid.gridy = 0;
		inputGrid.ipadx = insetsBetweenColumn;
		phoneInputPanel.add(new JLabel("Phone: "), inputGrid);
		inputGrid.gridx = 1;
		phoneInputPanel.add(this.phoneField, inputGrid);
		gridBag.gridy = 2;
		informationPanel.add(phoneInputPanel, gridBag);
		
		return informationPanel;
	}
	
	private JPanel buttonPanelFactory() {
		
		JPanel buttonPanel = new JPanel();
		GridBagConstraints gridBag = new GridBagConstraints();
		
		//Back
		JButton back = new JButton("Back");
		back.addActionListener(this);
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		buttonPanel.add(back, gridBag);
		
		//Finish
		JButton finish = new JButton("Finish");
		finish.addActionListener(this);
		gridBag.gridx = 1;
		buttonPanel.add(finish, gridBag);
		
		return buttonPanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		switch (e.getActionCommand()) {
		case "Back": {
			test.trainNoselectionPage.setVisible(true);
			test.payerInformationPage.setVisible(false);
			test.payerInformationPage.dispose();
			break;
		}
		case "Finish": {
			this.setPayerInfo();
			String code = this.insertTicketData();
			test.payerInformationPage.dispose();
			test.trainNoselectionPage.dispose();
			test.inquiryPage.dispose();
			test.inquiryPage = new TrainInquiry();
			test.inquiryPage.setVisible(true);
			Finish newPage = new Finish(code);
			newPage.setVisible(true);
		}
		}
	}
	
	private void setPayerInfo() {
		this.payerID = this.ID.getText();
		this.phone = this.phoneField.getText();
	}
	
	private String insertTicketData() {
		String code;
		do {
			code = this.randomCode();
		} while(this.repeatCode(code));
		
		this.insertBooking(code);
		
		if (this.discount == null) {
			this.insertTicket(code, "No discount", "100", 0, this.ticketCount);
		} else if (this.discount[1][0] == null) {
			this.insertTicket(code, "Early Discount", (String) this.discount[0][0], 0, (Integer) this.discount[0][1]);
		} else {
			this.insertTicket(code, "Early Discount", (String) this.discount[0][0], 0, (Integer) this.discount[0][1]);
			this.insertTicket(code, "Early Discount", (String) this.discount[1][0], 1, (Integer) this.discount[1][1]);
		}		
	
		return code;
	}

	private void insertTicket(String code, String discountType, String discount, int priceType, Integer ticketCount) {

		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
			           "root", "Timecompressor1919810");
			Statement stmt = conn.createStatement();
			String[][] seat = this.determineSeat(ticketCount);
			String insert = "";
			for (int i = 0; i < ticketCount; i++) {
				insert = String.format("insert into ticket values ('%s', '%s', %d, %d, '%s', '%s', '%s', '%s/%s/%s', %d, '%s%c', '%s');", 
						code, this.trainInfo[0], this.startStationID, this.endStationID, 
						seat[i][0], seat[i][1], seat[i][2],
						this.departureDate.get(Calendar.YEAR), this.departureDate.get(Calendar.MONTH) + 1, this.departureDate.get(Calendar.DATE),
						this.ticketPrice[priceType], discount, '%', discountType);
				//System.out.println(insert);
				stmt.execute(insert);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private String[][] determineSeat(int ticketCount) {
		
		String[][] seat = new String[ticketCount][3];
		
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
			           "root", "Timecompressor1919810");
			Statement stmt = conn.createStatement();
			String query = "";
			if (this.startStationID < this.endStationID) {
				query = String.format("select car, r, position\r\n"
						+ "from seat\r\n"
						+ "where type = '%s' and (car, r, position) not in\r\n"
						+ "(select car, r, position\r\n"
						+ "from ticket\r\n"
						+ "where trainNo = '%s' and departureDay = '%d/%d/%d' and (endStation %c= %d or startStation %c= %d));",
						this.ticketType, this.trainInfo[0], this.departureDate.get(Calendar.YEAR), this.departureDate.get(Calendar.MONTH) + 1, this.departureDate.get(Calendar.DATE),
						'>', this.startStationID, '<', this.endStationID);
			} else {
				query = String.format("select car, r, position\r\n"
						+ "from seat\r\n"
						+ "where type = '%s' and (car, r, position) not in\r\n"
						+ "(select car, r, position\r\n"
						+ "from ticket\r\n"
						+ "where trainNo = '%s' and departureDay = '%d/%d/%d' and (endStation %c= %d or startStation %c= %d));",
						this.ticketType, this.trainInfo[0], this.departureDate.get(Calendar.YEAR), this.departureDate.get(Calendar.MONTH) + 1, this.departureDate.get(Calendar.DATE),
						'<', this.startStationID, '>', this.endStationID);
			}
			ResultSet emptySeat = stmt.executeQuery(query);
			for (int i = 0; i < ticketCount; i++) {
				emptySeat.next();
				seat[i][0] = emptySeat.getString(1);
				seat[i][1] = emptySeat.getString(2);
				seat[i][2] = emptySeat.getString(3);
			}
			emptySeat.close();
			return seat;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	private void insertBooking(String code) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
			           "root", "Timecompressor1919810");
			Statement stmt = conn.createStatement();
			Calendar payDeadLine = (Calendar) this.departureDate.clone();
			payDeadLine.add(Calendar.DATE, -3);
			String insert = String.format("insert into booking values ('%s', '%s', '%s', '%d/%d/%d');",
					code, this.payerID, this.phone, payDeadLine.get(Calendar.YEAR),payDeadLine.get(Calendar.MONTH) + 1, payDeadLine.get(Calendar.DATE));
			//System.out.println(insert);
			stmt.execute(insert);
			//System.out.println("Booking inserted.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private String randomCode() {
		String code = "";
		int digit = (int) (Math.random() * 9 + 1);
		code = code.concat(String.valueOf(digit));
		for (int i = 0; i < 9; i++) {
			digit = (int) (Math.random() * 10);
			code = code.concat(String.valueOf(digit));
		}
		return code;
	}
	
	
private boolean repeatCode(String code) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
			           "root", "Timecompressor1919810");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery("select code from booking;");
			while (result.next()) {
				if (code.equals(result.getString(1))) {
					result.close();
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			return false;
		}
	}
}
