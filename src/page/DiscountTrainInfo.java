package page;


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
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class DiscountTrainInfo extends JFrame implements ActionListener, TrainInquirySystem {
	
	public class searchResultTable extends JPanel{
		
		private static final int insetsBetweenColumn = 15;

		public searchResultTable(ArrayList<Integer> train, int startStation, int endStation, Calendar departureDate) {
			this.setLayout(new GridBagLayout());
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = 0;
			gridBag.ipadx = insetsBetweenColumn;
			gridBag.fill = GridBagConstraints.BOTH;
			
			//Header row
			JLabel trainNoLabel = new JLabel("Train No");
			trainNoLabel.setOpaque(true);
			trainNoLabel.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx = 0;
			this.add(trainNoLabel, gridBag);
			
			JLabel earlyDiscountLabel = new JLabel("Early Discount");
			earlyDiscountLabel.setOpaque(true);
			earlyDiscountLabel.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			this.add(earlyDiscountLabel, gridBag);
			JLabel studentDiscountLabel = new JLabel("Student Discount");
			studentDiscountLabel.setOpaque(true);
			studentDiscountLabel.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			this.add(studentDiscountLabel, gridBag);
			JLabel departureTimeLabel = new JLabel("Departure Time");
			departureTimeLabel.setOpaque(true);
			departureTimeLabel.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			this.add(departureTimeLabel, gridBag);
			
			JLabel arriveTime = new JLabel("Arrive Time");
			arriveTime.setOpaque(true);
			arriveTime.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			this.add(arriveTime, gridBag);
			
			JLabel travelTime = new JLabel("Travel Time");
			travelTime.setOpaque(true);
			travelTime.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			this.add(travelTime, gridBag);
			
			this.insertRow(train, startStation, endStation, departureDate);
			
		}
		
		public void insertRow(ArrayList<Integer> train, int startStation, int endStation, Calendar departureDate) {
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = 0;
			gridBag.ipadx = insetsBetweenColumn;
			
			for (int i = 0; i < 10 && i < train.size(); i++) {
				
				int trainNo = train.get(i);
				gridBag.gridx = 0;
				gridBag.gridy++;
				this.add(new JLabel(String.valueOf(trainNo)), gridBag);
				
				gridBag.gridx++;
				this.add(new JLabel(this.getEarlyDiscount(trainNo, departureDate)), gridBag);
				
				gridBag.gridx++;
				this.add(new JLabel(this.getUniversityDiscount(trainNo, departureDate)), gridBag);
				
				gridBag.gridx++;
				this.add(new JLabel(SimpleQuery.getDepartureTime(trainNo, startStation)), gridBag);
				
				gridBag.gridx++;
				this.add(new JLabel(SimpleQuery.getDepartureTime(trainNo, endStation)), gridBag);
				
				gridBag.gridx++;
				this.add(new JLabel(SimpleQuery.getTravelTime(SimpleQuery.getDepartureTime(trainNo, startStation), SimpleQuery.getDepartureTime(trainNo, endStation))), gridBag);
			}
			
		}

		private String getEarlyDiscount(int trainNo, Calendar departureDate) {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
				           "root", test.PASSWORD);
				Statement stmt = conn.createStatement();
				String query = String.format(""
						+ "select discount "
						+ "from earlydiscount "
						+ "where trainNo = %d and serviceday = '%s' "
						+ "and tickets > (select count(*) from ticket where trainNo = %d and departureDay = '%s/%s/%s' and discountType = 'Early Discount' and earlydiscount.discount = ticket.discount) "
						+ "order by discount asc;", 
						trainNo, SimpleQuery.getDay(departureDate), trainNo,  departureDate.get(Calendar.YEAR), departureDate.get(Calendar.MONTH) + 1, departureDate.get(Calendar.DATE));
				ResultSet result = stmt.executeQuery(query);

				if (!result.next()) {
					return "*";
				} else {
					String discount = result.getString(1);
					result.close();
					return String.format("%s%c", discount, '%');
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		private String getUniversityDiscount(int trainNo, Calendar departureDate) {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
				           "root", test.PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(String.format(""
						+ "select %s "
						+ "from universityDiscount "
						+ "where trainNo = %d;", SimpleQuery.getDay(departureDate), trainNo));
				
				if (!result.next() || result.getString(1).equals("100")) {
					return "*";
				} else {
					String discount = result.getString(1);
					result.close();
					return String.format("%s%c", discount, '%');
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}

	}
	
	public DiscountTrainInfo(ArrayList<Integer> train, int startStation, int endStation, Calendar departureDate) {
		this.setTitle("Choose Train No.");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(TrainInquirySystem.WIDTH, TrainInquirySystem.HEIGHT);
		this.setLayout(new BorderLayout());
		
		this.add(new Header(), BorderLayout.NORTH);
		JPanel positionPanel = new JPanel();
		positionPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gridBag = new GridBagConstraints();
		gridBag.gridx = 0;
		gridBag.gridy = 0;
		
		gridBag.gridy = 0;
		gridBag.anchor = GridBagConstraints.WEST;
		positionPanel.add(new JLabel("Train Info"), gridBag);
		gridBag.gridy = 1;
		gridBag.anchor = GridBagConstraints.CENTER;
		positionPanel.add(new searchResultTable(train, startStation, endStation, departureDate), gridBag);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton backButton = new JButton("Back");
		backButton.addActionListener(this);
		buttonPanel.add(backButton, BorderLayout.WEST);
		gridBag.gridy = 2;
		positionPanel.add(buttonPanel, gridBag);
		this.add(positionPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.setVisible(false);
		this.dispose();
		test.searchDiscountTrainPage.setVisible(true);
	}

}
