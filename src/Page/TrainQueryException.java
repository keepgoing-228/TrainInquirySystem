package page;

public class TrainQueryException extends Exception {

	public static enum exceptionType{SAME_START_END_STATION, NO_ACCESSABLE_TICKET, WRONG_NUMBERS_OF_TICKET, WRONG_ID_LENGTH, WRONG_PHONE_LENGTH, ILLEGAL_DEPARTURE_DATE, WRONG_TICKET_INFO, ILLEGAL_TRAINNO, EMPTY_VALUE, ILLEGAL_TIME};
	public exceptionType type;
	
	public TrainQueryException(exceptionType type) {
		this.type = type;
	}
	
	public String getMessage() {
		switch (this.type) {
		case SAME_START_END_STATION: return "Please choose different start station and end station.";
		case NO_ACCESSABLE_TICKET: return "No accessable ticket, please find other deliver date.";
		case WRONG_NUMBERS_OF_TICKET: return "Please enter a number between 0 and 10.";
		case ILLEGAL_DEPARTURE_DATE: return "Only selling tickets in next 28 days";
		case WRONG_ID_LENGTH: return "Please enter an ID in length 10.";
		case WRONG_PHONE_LENGTH: return "Please enter a phone number in length 10.";
		case WRONG_TICKET_INFO: return "No such ticket found, please check your input.";
		case ILLEGAL_TRAINNO: return "TrainNo is a number";
		case EMPTY_VALUE: return "There is an empty value in your input";
		case ILLEGAL_TIME: return "Departure time must before arrive time";
		default: return null;
		}
	}
}
