package Page;
import java.util.Calendar;

import javax.swing.JFrame;

public class test {
	public static TrainInquiry inquiryPage;
	public static TrainNoSelection trainNoselectionPage;
	public static PayerInformation payerInformationPage;
	
	public static void main(String[] args) {
		inquiryPage = new TrainInquiry();
		inquiryPage.setVisible(true);
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

}
