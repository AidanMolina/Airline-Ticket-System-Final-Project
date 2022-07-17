import java.awt.Point;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Flight {

	private static final int KILOMETERSPERKILOGRAMOFFUEL = 3;
	
	private ArrayList<Passenger> passengers;
	private boolean[][] seats;

	private Airline airline;
	private int id;
	
	private ArrayList<FlightStop> route;
	private FlightStop departure;
	private FlightStop arrival;
	private ArrayList<FlightStop> layovers;
	
	private LocalDateTime startTime;
	
	private int remainingDistance;
	private int fuel;
	private int speed;

	public int getBasePrice(){
		int i = 500 + getStartingTotal() / 5;
		return i;
	}
	
	Flight(int id, Airline airline, ArrayList<FlightStop> route, int speed, int fuel, int numberOfRows, int numberOfSeatsInEachRow, LocalDateTime departureTime) {
		// Entire route
		this.route = route;
		
		// Start destination
		this.departure = route.get(0);
		
		// End destination
		this.arrival = route.get(route.size() - 1);
		
		// In between
		this.layovers = route;
		this.layovers.remove(0);
		this.layovers.remove(layovers.size() - 1);
		
		// Total distance of flight (in kilometers)
		this.remainingDistance = getStartingTotal();
		
		// In kilograms
		this.fuel = fuel;
		
		// In kilometers per hour
		this.speed = speed;
		
		// This flight's unique id
		this.id = id;
		this.airline = airline;
		
		
		// True is filled, and false is not filled
		this.seats = new boolean[numberOfRows][numberOfSeatsInEachRow];

	}
	
	public void addPassenger(Passenger passenger) {
		if (passenger.getTicket().getFlight().getId() == id) {
			passengers.add(passenger);
		
			int seatInRow = 0;
			for (int i = (int)'a'; i < (int)'a' + 26; i++) {
				if (passenger.getWhichSeatInRow() == (char)i) {
					seatInRow = i - (int)'a';
				}
			}
			seats[passenger.getSeatRow()][seatInRow] = true;
		}
	}
	
	public boolean isFull() {
		boolean result = true;
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; i < seats[i].length; j++) {
				result = result && seats[i][j];
			}
		}
		return result;
	}
	
	// Flight time in hours
	public int howManyHours() {
		return getRemainingDistance()/speed;
	}
/*
	public void fly() {
		int elapsedTime = (int) (System.currentTimeMillis() - startTime); 
		elapsedTime /= 1000;
		elapsedTime /= 60;
		elapsedTime /= 60;
		setRemainingDistance(getRemainingDistance() - elapsedTime*speed);
		setFuel(getFuel()-elapsedTime*speed/KILOMETERSPERKILOGRAMOFFUEL);
	}
	
	public boolean hasArrived() {
		if (getRemainingDistance() <= 0) {return true;}
		else return false;
	}
	
	public void startTime() {
		if (departure.getTime().isEqual(LocalDateTime.now())) {
			startTime = System.currentTimeMillis();
		}
	}

 */
	
	public static double distance(Point one, Point two) {
		double distance = Math.sqrt(Math.pow(one.getX() - two.getX(), 2) + Math.pow(one.getY() - two.getY(), 2));
		return distance;
	}	
	
	public double totalDistanceByDegrees(ArrayList<FlightStop> theStops) {
		ArrayList<FlightStop> stops = theStops;
		if (stops.size() <= 1) {return 0;}
		if (stops.size() == 2) {return distance(stops.get(0).getLocation(), stops.get(1).getLocation());}
		else {
			stops.remove(0);
			return distance(stops.get(0).getLocation(), stops.get(1).getLocation()) + totalDistanceByDegrees(stops);}
	}
	
	public int getStartingTotal() {
		double kmInOneDegree = 111.0;
		int totalDistance = (int) Math.round(totalDistanceByDegrees(route) * kmInOneDegree);
		return totalDistance;
	}	
	
	// Getters and setters
	
	public ArrayList<Passenger> getPassengers() {
		return passengers;
	}


	public void setPassengers(ArrayList<Passenger> passengers) {
		this.passengers = passengers;
	}


	public boolean[][] getSeats() {
		return seats;
	}


	public void setSeats(boolean[][] seats) {
		this.seats = seats;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<FlightStop> getRoute() {
		return route;
	}


	public void setRoute(ArrayList<FlightStop> route) {
		this.route = route;
	}


	public FlightStop getDeparture() {
		return departure;
	}


	public void setDeparture(FlightStop departure) {
		this.departure = departure;
	}


	public FlightStop getArrival() {
		return arrival;
	}


	public void setArrival(FlightStop arrival) {
		this.arrival = arrival;
	}


	public ArrayList<FlightStop> getLayovers() {
		return layovers;
	}


	public void setLayovers(ArrayList<FlightStop> layovers) {
		this.layovers = layovers;
	}


	public int getFuel() {
		return fuel;
	}


	public void setFuel(int fuel) {
		this.fuel = fuel;
	}

	public int getRemainingDistance() {
		return this.remainingDistance;
	}
	
	public void setRemainingDistance(int distance) {
		this.remainingDistance = distance;
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	
	
	
}

