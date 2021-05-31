import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class TrainNoSelection extends JFrame implements ActionListener, TrainInquirySystem {
	
	public class searchResultTable extends JPanel implements ActionListener{
		
		private static final int insetsBetweenColumn = 15;
		public ButtonGroup selectGroup = new ButtonGroup();
		private int length = 0;
		private int selection;

		public int getSelection() {
			return selection;
		}

		public void setSelection(int selection) {
			this.selection = selection;
		}

		public searchResultTable() {
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
			
			for (int i = 1; i < 10; i++) {
				this.insertRow(i);
			}
		}
		
		public void insertRow(int row) {
			GridBagConstraints gridBag = new GridBagConstraints();
			gridBag.gridx = 0;
			gridBag.gridy = row;
			gridBag.ipadx = insetsBetweenColumn;
			
			JRadioButton selection = new JRadioButton();
			selectGroup.add(selection);
			int index = this.length;
			selection.addActionListener(this);
			selection.setActionCommand(String.valueOf(index));
			gridBag.gridx = 0;
			this.add(selection, gridBag);
			
			JLabel trainNo = new JLabel("1000");
			gridBag.gridx = 1;
			this.add(trainNo, gridBag);
			
			JLabel earlyDiscount = new JLabel();
			gridBag.gridx = 2;
			this.add(earlyDiscount, gridBag);
			
			JLabel dipartureTime = new JLabel("12:00");
			gridBag.gridx = 3;
			this.add(dipartureTime, gridBag);
			
			JLabel arriveTime = new JLabel("14:00");
			gridBag.gridx = 4;
			this.add(arriveTime, gridBag);
			
			JLabel travelTime = new JLabel("2:00");
			gridBag.gridx = 5;
			this.add(travelTime, gridBag);
			
			this.length++;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			this.selection = Integer.parseInt(e.getActionCommand());
		}
	}
	
	public TrainNoSelection() {
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
		positionPanel.add(new JLabel("From Taipei to TaoYuan"), gridBag);
		gridBag.gridy = 1;
		gridBag.anchor = GridBagConstraints.CENTER;
		positionPanel.add(new searchResultTable(), gridBag);
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BorderLayout());
		JButton backButton = new JButton("Back");
		JButton selectButton = new JButton("Select");
		buttonPanel.add(backButton, BorderLayout.WEST);
		buttonPanel.add(selectButton, BorderLayout.EAST);
		gridBag.gridy = 2;
		positionPanel.add(buttonPanel, gridBag);
		this.add(positionPanel, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args) {
		TrainNoSelection aPage = new TrainNoSelection();
		aPage.setVisible(true);

	}


}
