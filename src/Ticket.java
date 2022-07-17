import java.time.LocalDateTime;


public class Ticket {
	public int price;
	public boolean isFirstClass;
	public Flight flight;
	public LocalDateTime dateBooked;
	
	private int seatRow;
	private char whichSeatInRow;
	
	//constructor
	public Ticket (Flight ourFlight, LocalDateTime dateBooked, Boolean isFirstClass, int seatRow, char whichSeatInRow) {
		this.flight = ourFlight;
		this.dateBooked = dateBooked;
		this.isFirstClass = isFirstClass;
		this.seatRow = seatRow;
		this.whichSeatInRow = whichSeatInRow;
	}
	
	//sets the price of the ticket based on first class and flight distance
	public void setPrice () {
		/*if (isFirstClass)
			price = 1000 + flight.getStartingTotal()/5;
		else
		 */
			price = 500 + flight.getStartingTotal()/5;
	}
	
	public int getPrice() {
		return price;
	}
	
	//prints all the information on the ticket
	public String ticketInfo() {
		if (isFirstClass)
			return "First Class flight to " + flight.getArrival() + " from " + flight.getDeparture() + " booked on " + dateBooked;
		else
			return "Coach flight to " + flight.getArrival() + " from " + flight.getDeparture() + " booked on " + dateBooked;
	}

	public boolean isFirstClass() {
		return isFirstClass;
	}

	public void setFirstClass(boolean isFirstClass) {
		this.isFirstClass = isFirstClass;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public LocalDateTime getDateBooked() {
		return dateBooked;
	}

	public void setDateBooked(LocalDateTime dateBooked) {
		this.dateBooked = dateBooked;
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
	
	
}
