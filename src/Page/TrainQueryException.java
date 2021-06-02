package Page;

public class TrainQueryException extends Exception {

	public static enum exceptionType{SAME_START_END_STATION, NO_ACCESSABLE_TICKET, WRONG_NUMBERS_OF_TICKET};
	public exceptionType type;
	
	public TrainQueryException(exceptionType type) {
		this.type = type;
	}
	
	public String getMessage() {
		switch (this.type) {
		case SAME_START_END_STATION: return "Please choose different start station and end station.";
		case NO_ACCESSABLE_TICKET: return "No accessable ticket, please find other deliver date.";
		case WRONG_NUMBERS_OF_TICKET: return "Please enter a number between 0 and 10.";
		default: return null;
		}
	}
}
