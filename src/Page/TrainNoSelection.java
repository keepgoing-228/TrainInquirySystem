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
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class TrainNoSelection extends JFrame implements ActionListener, TrainInquirySystem {
	
	private ArrayList<String> trainNoList;
	private int startStationID, endStationID, ticketCount;
	String ticketType;
	Calendar departureDate;
	private SearchResultTable searchResultTable;
	
	
	public class SearchResultTable extends JPanel implements ActionListener{
		
		private static final int insetsBetweenColumn = 15;
		public ButtonGroup selectGroup = new ButtonGroup();
		private String[][] trainInfo = new String[10][3];
		private JRadioButton[] buttonList = new JRadioButton[10];
		private Object[][][] discountList = new Object[10][2][2];

		public String[][] getTrainInfo() {
			return this.trainInfo.clone();
		}
		
		public Object[][][] getDiscountList(){
			return this.discountList.clone();
		}
		
		public int getSelection() {
	        for (int i = 0; i < buttonList.length; i++) {
	        	if (buttonList[i].isSelected()) {
	        		return i;
	        	}
	        }
			return -1;
		}

		public SearchResultTable() {
			this.setLayout(new GridBagLayout());
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = 0;
			gridBag.ipadx = insetsBetweenColumn;
			gridBag.fill = GridBagConstraints.BOTH;
			
			//Header row
			JLabel selectLabel = new JLabel("Select");
			selectLabel.setOpaque(true);
			selectLabel.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx = 0;
			this.add(selectLabel, gridBag);
			
			JLabel trainNoLabel = new JLabel("Train No");
			trainNoLabel.setOpaque(true);
			trainNoLabel.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx = 1;
			this.add(trainNoLabel, gridBag);
			
			JLabel earlyDiscountLabel = new JLabel("Early Discount");
			earlyDiscountLabel.setOpaque(true);
			earlyDiscountLabel.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx = 2;
			this.add(earlyDiscountLabel, gridBag);
			
			JLabel departureTimeLabel = new JLabel("Departure Time");
			departureTimeLabel.setOpaque(true);
			departureTimeLabel.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx = 3;
			this.add(departureTimeLabel, gridBag);
			
			JLabel arriveTime = new JLabel("Arrive Time");
			arriveTime.setOpaque(true);
			arriveTime.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx = 4;
			this.add(arriveTime, gridBag);
			
			JLabel travelTime = new JLabel("Travel Time");
			travelTime.setOpaque(true);
			travelTime.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx = 5;
			this.add(travelTime, gridBag);
			
			for (int i = 1; i < 11; i++) {
				this.insertRow(trainNoList.get(i), i);
			}
			
		}
		
		public void insertRow(String trainNo, int row) {
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = row;
			gridBag.ipadx = insetsBetweenColumn;
			
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
				           "root", "Timecompressor1919810");
				Statement query = conn.createStatement();
				ResultSet result;
				
				this.buttonList[row - 1] = new JRadioButton();
				selectGroup.add(this.buttonList[row - 1]);
				this.buttonList[row - 1].setActionCommand(String.valueOf(row - 1));
				gridBag.gridx = 0;
				this.add(this.buttonList[row - 1], gridBag);
				
				JLabel trainNoLabel = new JLabel(trainNo);
				gridBag.gridx = 1;
				this.add(trainNoLabel, gridBag);
				this.trainInfo[row - 1][0] = trainNo;
				
				Object[][] discountSet = checkDiscount(trainNo);
				this.discountList[row - 1] = discountSet;
				String earlyDiscountString;
				if (discountSet == null) {
					earlyDiscountString = "";
				} else {
					if (discountSet[1][0] == null) {
						earlyDiscountString = String.format("%s%c discount", discountSet[0][0], '%');
					} else {
						earlyDiscountString = String.format("%s%c discount, %d tickets; %s%c discount, %d tickets", discountSet[0][0], '%', discountSet[0][1], discountSet[1][0], '%', discountSet[1][1]);
					}
				}
				JLabel earlyDiscountLabel = new JLabel(earlyDiscountString);
				gridBag.gridx = 2;
				this.add(earlyDiscountLabel, gridBag);
				
				result = query.executeQuery(String.format("select departureTime from stop where trainNo = %s and ID = %d", trainNo, startStationID));
				result.next();
				trainInfo[row - 1][1] = result.getString("departureTime").substring(0, 5);
				JLabel departureTimeLabel = new JLabel(result.getString("departureTime").substring(0, 5));
				gridBag.gridx = 3;
				this.add(departureTimeLabel, gridBag);
				int departureTime = result.getTime("departureTime").getHours() * 60 + result.getTime("departureTime").getMinutes();
				result.close();
				
				result = query.executeQuery(String.format("select departureTime from stop where trainNo = %s and ID = %d", trainNo, endStationID));
				result.next();
				trainInfo[row - 1][2] = result.getString("departureTime").substring(0, 5);
				JLabel arriveTimeLabel = new JLabel(result.getString("departureTime").substring(0, 5));
				gridBag.gridx = 4;
				this.add(arriveTimeLabel, gridBag);
				int arriveTime = result.getTime("departureTime").getHours() * 60 + result.getTime("departureTime").getMinutes();
				result.close();

				int travelTime = arriveTime - departureTime;
				JLabel travelTimeLabel = new JLabel(String.format("%d:%d", travelTime / 60, travelTime % 60));
				gridBag.gridx = 5;
				this.add(travelTimeLabel, gridBag);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		private Object[][] checkDiscount(String trainNo) {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
				           "root", "Timecompressor1919810");
				Statement stmt1 = conn.createStatement();
				Statement stmt2 = conn.createStatement();
				ResultSet totalEarlyDiscountTickets = stmt1.executeQuery(String.format("select discount, tickets "
						+ "from earlyDiscount "
						+ "where trainNo = %s and serviceday = '%s' "
						+ "order by discount asc;", 
						trainNo, test.getDay(departureDate)));
				ResultSet earlyDiscountTickets = stmt2.executeQuery(String.format("select discount, count(*) "
						+ "from ticket "
						+ "where trainNo = %s and departureDay = '%d/%d/%d' and discountType = 'earlyDiscount' "
						+ "group by discount "
						+ "order by discount asc;", 
						trainNo, departureDate.get(Calendar.YEAR), departureDate.get(Calendar.MONTH) + 1, departureDate.get(Calendar.DATE)));
				int totalTickets, tickets;
				Object[][] result = null;
				if (!totalEarlyDiscountTickets.next()) {
					return result;
				}
				if (!earlyDiscountTickets.next()) {
					result = new Object[2][2];
					result[0][0] = totalEarlyDiscountTickets.getString("discount");
					result[0][1] = ticketCount;
					return result;
				}
				do {
					if (earlyDiscountTickets.isAfterLast()) {
						result = new Object[2][2];
						result[0][0] = totalEarlyDiscountTickets.getString("discount");
						result[0][1] = ticketCount;
						break;
					}
					totalTickets = totalEarlyDiscountTickets.getInt("tickets");
					tickets = earlyDiscountTickets.getInt("count(*)");
					if (tickets + ticketCount < totalTickets) {
						result = new Object[2][2];
						result[0][0] = totalEarlyDiscountTickets.getString("discount");
						result[0][1] = ticketCount;
						break;
					} else if (tickets < totalTickets){
						result = new Object[2][2];
						result[0][0] = totalEarlyDiscountTickets.getString("discount");
						result[0][1] = totalTickets - tickets;
						totalEarlyDiscountTickets.next();
						if (totalEarlyDiscountTickets.isAfterLast()) {
							break;
						} else {
							result[1][0] = totalEarlyDiscountTickets.getString("discount");
							result[1][1] = ticketCount - (totalTickets - tickets);
							break;
						}
					}
					earlyDiscountTickets.next();
					totalEarlyDiscountTickets.next();
				} while (!totalEarlyDiscountTickets.isAfterLast());
				System.out.printf("Hi %s %d\n", result[0][0], result[0][1]);
				return result;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
		}
	}
	
	public TrainNoSelection(ArrayList<String> trainNoList, int startStationID, int endStationID, String ticketType, Calendar departureDate, int ticketCount) {
		this.setTitle("Train Inquiry system");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(TrainInquirySystem.WIDTH, TrainInquirySystem.HEIGHT);
		this.setLayout(new BorderLayout());
		this.trainNoList = trainNoList;
		this.startStationID = startStationID;
		this.endStationID = endStationID;
		this.ticketType = ticketType;
		this.departureDate = departureDate;
		this.ticketCount = ticketCount;
		
		this.add(new Header(), BorderLayout.NORTH);
		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		
		gridBag.gridy = 0;
		gridBag.anchor = GridBagConstraints.WEST;
		String startStation = "";
		String endStation = "";
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
			           "root", "Timecompressor1919810");
			Statement stmt = conn.createStatement();
			String searchStation = String.format("select enName from station where ID = %d;", startStationID);
			ResultSet station = stmt.executeQuery(searchStation);
			station.next();
			startStation = station.getString("enName");
			searchStation = String.format("select enName from station where ID = %d;", endStationID);
			station = stmt.executeQuery(searchStation);
			station.next();
			endStation = station.getString("enName");
		} catch(SQLException e) {
			System.out.println(e.getMessage());
		}
		String topLabel = String.format("From %s to %s %d/%d (%s)", startStation, endStation, departureDate.get(Calendar.MONTH) + 1, departureDate.get(Calendar.DATE), test.getDay(departureDate));
		positionPanel.add(new JLabel(topLabel), gridBag);
		gridBag.gridy = 1;
		gridBag.anchor = GridBagConstraints.CENTER;
		searchResultTable = new SearchResultTable();
		positionPanel.add(searchResultTable, gridBag);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		JButton selectButton = new JButton("Check");
		selectButton.addActionListener(this);
		buttonPanel.add(backButton, BorderLayout.WEST);
		buttonPanel.add(selectButton, BorderLayout.EAST);
		gridBag.gridy = 2;
		positionPanel.add(buttonPanel, gridBag);
		this.add(positionPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "Back":{
			test.inquiryPage.setVisible(true);
			this.setVisible(false);
			this.dispose();
			break;
		}
		case "Check":{
			String[][] trainInfoList = searchResultTable.getTrainInfo();
			int selection = searchResultTable.getSelection();
			String[] trainInfo = trainInfoList[selection];
			Object[][] discount = searchResultTable.getDiscountList()[selection];
			test.payerInformationPage = new PayerInformation(trainInfo, discount, this.departureDate, this.startStationID, this.endStationID, this.ticketCount, this.ticketType);
			test.payerInformationPage.setVisible(true);
			this.setVisible(false);
			break;
		}
		}

	}

}
