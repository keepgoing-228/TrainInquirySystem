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
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class TimeTable extends JFrame implements ActionListener, TrainInquirySystem {
	
	public class searchResultTable extends JPanel implements ActionListener{
		
		private static final int insetsBetweenColumn = 15;
		private Calendar departureDate;
		private int direction;
		private JButton front10, next10;
		private JPanel timeTable;
		int index = 0;
		int totalTrainCount = 0;

		public searchResultTable(Calendar departureDate, int direction) {
			this.departureDate = departureDate;
			this.direction = direction;
			this.setTotalTrainCount();
			this.buildPanel();

		}
		
		private void setTotalTrainCount() {
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
				           "root", test.PASSWORD);
				Statement stmt = conn.createStatement();
				ResultSet result = stmt.executeQuery(String.format("select count(*) "
						+ "from train natural join serviceday "
						+ "where day = '%s' and direction = %d;", SimpleQuery.getDay(this.departureDate), this.direction));
				result.next();
				this.totalTrainCount = result.getInt(1);
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

		public JPanel timeTableFactory() {
			JPanel timeTable = new JPanel();
			timeTable.setLayout(new GridBagLayout());
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = 0;
			gridBag.ipadx = insetsBetweenColumn;
			gridBag.fill = GridBagConstraints.BOTH;
			
			//Header row	
			JLabel trainNoLabel = new JLabel("Train No");
			trainNoLabel.setOpaque(true);
			trainNoLabel.setBackground(Color.LIGHT_GRAY);
			timeTable.add(trainNoLabel, gridBag);
			
			JLabel 南港 = new JLabel("南港");
			南港.setOpaque(true);
			南港.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(南港, gridBag);
			
			JLabel 台北 = new JLabel("台北");
			台北.setOpaque(true);
			台北.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(台北, gridBag);
			
			JLabel 板橋 = new JLabel("板橋");
			板橋.setOpaque(true);
			板橋.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(板橋, gridBag);

			JLabel 桃園 = new JLabel("桃園");
			桃園.setOpaque(true);
			桃園.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(桃園, gridBag);
			
			JLabel 新竹 = new JLabel("新竹");
			新竹.setOpaque(true);
			新竹.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(新竹, gridBag);
			
			JLabel 苗栗 = new JLabel("苗栗");
			苗栗.setOpaque(true);
			苗栗.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(苗栗, gridBag);
			
			JLabel 台中 = new JLabel("台中");
			台中.setOpaque(true);
			台中.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(台中, gridBag);
			
			JLabel 彰化 = new JLabel("彰化");
			彰化.setOpaque(true);
			彰化.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(彰化, gridBag);
			
			JLabel 雲林 = new JLabel("雲林");
			雲林.setOpaque(true);
			雲林.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(雲林, gridBag);
			
			JLabel 嘉義 = new JLabel("嘉義");
			嘉義.setOpaque(true);
			嘉義.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(嘉義, gridBag);
			
			JLabel 台南 = new JLabel("台南");
			台南.setOpaque(true);
			台南.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(台南, gridBag);
			
			JLabel 左營 = new JLabel("左營");
			左營.setOpaque(true);
			左營.setBackground(Color.LIGHT_GRAY);
			gridBag.gridx++;
			timeTable.add(左營, gridBag);
			
			this.insertRow(timeTable);
			
			return timeTable;
		}

		public void insertRow(JPanel timeTable) {
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = 0;
			gridBag.ipadx = insetsBetweenColumn;
			
			try {
				Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
				           "root", test.PASSWORD);
				Statement stmt1 = conn.createStatement();
				SimpleQuery.getDay(departureDate);
				
				ResultSet train = stmt1.executeQuery(String.format("select trainNo "
						+ "from train natural join serviceday "
						+ "where day = '%s' and direction = %d "
						+ "order by trainNo asc "
						+ "limit %d, 10;", SimpleQuery.getDay(this.departureDate), this.direction, this.index));
				train.next();
				int[] id = {990, 1000, 1010, 1020, 1030, 1035, 1040, 1043, 1047, 1050, 1060, 1070};
				Statement stmt2 = conn.createStatement();
				ResultSet time;
				for (int i = 1; i < 11; i++) {
					
					gridBag.gridx = 0;
					gridBag.gridy = i;
					timeTable.add(new JLabel(train.getString(1)), gridBag);
					
					for (int j = 0; j < id.length; j++) {
						time = stmt2.executeQuery(String.format("select departureTime "
								+ "from stop "
								+ "where trainNo = %d and ID = %d;", train.getInt(1), id[j]));
						gridBag.gridx++;
						if (!time.next()) {
							timeTable.add(new JLabel("*"), gridBag);
						} else {
							timeTable.add(new JLabel(time.getString(1).substring(0, 5)), gridBag);
						}
						time.close();
					}
					
					train.next();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
	
		private JPanel buttonPanelFactory() {

			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridBagLayout());
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			
			this.front10 = new JButton("Front 10");
			front10.addActionListener(this);
			buttonPanel.add(front10, gridBag);
			
			gridBag.gridx++;
			this.next10 = new JButton("Next 10");
			next10.addActionListener(this);
			buttonPanel.add(next10, gridBag);
			
			return buttonPanel;
		}

		private void buildPanel() {
			this.setLayout(new GridBagLayout());
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = 0;
			
			this.timeTable = this.timeTableFactory();
			this.add(this.timeTable, gridBag);
						
			JPanel buttonPanel = this.buttonPanelFactory();
			gridBag.gridy++;
			this.add(buttonPanel, gridBag);
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = 1;
			
			switch (e.getActionCommand()) {
			case "Front 10":{
				if (this.index - 10 < 0) {
					this.index = 0;
				} else {
					this.index -= 10;
				}
				this.removeAll();
				this.buildPanel();
				this.revalidate();
				System.out.print(this.index);
				break;
			}
			case "Next 10":{
				if (this.index + 20 > this.totalTrainCount) {
					this.index = this.totalTrainCount - 11;
				} else {
					this.index += 10;

				}
				this.removeAll();
				this.buildPanel();
				this.revalidate();
				System.out.print(this.index);
				break;
			}
			}
			
		}
	}
	
	public TimeTable(Calendar departureDate, int direction) {
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
		
		if (direction == 1) {
			positionPanel.add(new JLabel("To North"), gridBag);
		} else {
			positionPanel.add(new JLabel("To South"), gridBag);
		}
		
		gridBag.gridy = 1;
		gridBag.anchor = GridBagConstraints.CENTER;
		positionPanel.add(new searchResultTable(departureDate, direction), gridBag);
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
		test.searchTimeTablePage.setVisible(true);
	}

}
