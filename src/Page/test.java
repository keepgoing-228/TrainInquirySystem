package page;

import javax.swing.JFrame;

public class test {
	public static TrainInquiry inquiryPage;
	public static TrainNoSelection trainNoSelectionPage;
	public static PayerInformation payerInformationPage;
	public static TicketQuery ticketQueryPage;
	public static SearchTimeTable searchTimeTablePage;
	public static TimeTable timeTablePage;
	public static SearchDiscountTrain searchDiscountTrainPage;
	public static DiscountTrainInfo discountTrainInfoPage;
	public static final String PASSWORD = "Timecompressor1919810";
	
	public static void main(String[] args) {
		inquiryPage = new TrainInquiry();
		inquiryPage.setVisible(true);
	}

	public static void disposeAllPage() {
		
		if (inquiryPage != null) {
			inquiryPage.dispose();
		}
		
		if (trainNoSelectionPage != null) {
			trainNoSelectionPage.dispose();
		}
		
		if (payerInformationPage != null) {
			payerInformationPage.dispose();
		}
		
		if (ticketQueryPage != null) {
			ticketQueryPage.dispose();
		}
		
		if (searchTimeTablePage != null) {
			searchTimeTablePage.dispose();
		}
		
		if (timeTablePage != null) {
			timeTablePage.dispose();
		}
		
		if (searchDiscountTrainPage != null) {
			searchDiscountTrainPage.dispose();
		}
		
		if (discountTrainInfoPage != null) {
			discountTrainInfoPage.dispose();
		}
		
	}
	
}
