package page;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.JComboBox;

public class SimpleQuery {

	/**
	 * @param enName English name of the station
	 * @return ID of the station
	 */
	public static int getStationID(String enName){
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
					test.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(String.format("select ID from station where enName = '%s';", enName));
			result.next();
			return result.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	public static String getDay(Calendar date) {
		switch (date.get(Calendar.DAY_OF_WEEK)) {
		case 1: return "Sunday";
		case 2: return "Monday";
		case 3: return "Tuesday";
		case 4: return "Wednesday";
		case 5: return "Thursday";
		case 6: return "Friday";
		case 7: return "Saturday";
		default: return null;
		}
	}
	
	public static JComboBox<String> stationComboBoxFactory() {
		JComboBox<String> comboBox = new JComboBox<String>();
		String[] stationName = {"Nangang", "Taipei", "Banciao", "Taoyuan", "Hsinchu", "Miaoli", "Taichung", "Changhua", "Yunlin", "Chiayi", "Tainan", "Zuoying"};
		for (int i = 0; i < stationName.length; i++) {
			comboBox.addItem(stationName[i]);
		}
		return comboBox;
	}
	
	public static String getStationName(int stationID) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
					test.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(String.format("select enName from station where id = '%d';", stationID));
			result.next();
			return result.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}

	public static String getDepartureTime(int trainNo, int stationID) {
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/highspeedrail", "root",
					test.PASSWORD);
			Statement stmt = conn.createStatement();
			ResultSet result = stmt.executeQuery(String.format("select departureTime from stop where trainNo = %d and ID = %d;",
					trainNo, stationID));
			result.next();
			return result.getString(1).substring(0, 5);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}

	public static String getTravelTime(String departureTime, String arriveTime) {
		
		String result = null;
		int departure = Integer.parseInt(departureTime.substring(0, 2)) * 60 + Integer.parseInt(departureTime.substring(3, 5));
		int arrive = Integer.parseInt(arriveTime.substring(0, 2)) * 60 + Integer.parseInt(arriveTime.substring(3, 5));
		result = String.format("%d:%02d", (arrive - departure) / 60, (arrive - departure) % 60);
		
		return result;
	}
	
}
