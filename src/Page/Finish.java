package Page;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Finish extends JFrame {

	public Finish(String code) {
		this.setTitle("Train Inquiry system");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(300, 150);
		this.setLayout(new GridBagLayout());
		
		String payDeadLine = "";
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail",
			           "root", "Timecompressor1919810");
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(String.format("select payDeadLine from booking where code = '%s';", code));
			result.next();
			payDeadLine = result.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		this.add(new JLabel("Please pay before " + payDeadLine), c);
		c.gridy = 1;
		this.add(new JLabel("Code: " + code), c);
	}
}
