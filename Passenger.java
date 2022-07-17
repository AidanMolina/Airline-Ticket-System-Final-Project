
public class Passenger {
	
	private UserAccount account;
	private boolean firstClass;
	
	private int seatRow;
	private char whichSeatInRow;
	
	private Airline airline;
	private Ticket ticket;
	private int numOfBags;
	
	Passenger(UserAccount account, boolean flightClass, Airline airline, Ticket ticket, int numOfBags) {
		this.account = account;
		
		this.firstClass = flightClass;
		
		this.airline = airline;
		
		this.ticket = ticket;
		
		this.seatRow = ticket.getSeatRow();
		
		this.whichSeatInRow = ticket.getWhichSeatInRow();
		this.numOfBags = numOfBags;
	}
	
	public boolean onNoFlyList() {
		if (airline.getNoFlyList().contains(account)) return true;
		else return false;
	}
	
	public void changeFlightClass(String flightClass) {
		if (account.isPaidForClassUpgrade()) {this.firstClass = true;}
	}
	
	public void changeSeat() {
		if (account.isPaidForSeatChange()) {
			this.seatRow = ticket.getSeatRow();
			this.whichSeatInRow = ticket.getWhichSeatInRow();
		}
	}

	// Getters and setters
	public UserAccount getAccount() {
		return account;
	}

	public void setAccount(UserAccount account) {
		this.account = account;
	}

	public Boolean getFirstClass() {
		return firstClass;
	}

	public void setFirstClass(Boolean flightClass) {
		this.firstClass = flightClass;
	}

	public int getSeatRow() {
		return seatRow;
	}

	public void setSeatRow(int seatRow) {
		this.seatRow = seatRow;
	}

	public char getWhichSeatInRow() {
		return whichSeatInRow;
	}

	public void setWhichSeatInRow(char whichSeatInRow) {
		this.whichSeatInRow = whichSeatInRow;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}
	
}
