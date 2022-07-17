import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.time.LocalDateTime;
import java.io.*;

public class UserAirlineInterface {

	private ArrayList<UserAccount> users;
	private ArrayList<Airline> airlines;
	private UserAccount currentUser;
	private Airline currentAirline;

	// constructor
	public UserAirlineInterface(){
		this.users = UserAccount.getUserAccountArray("userAccounts.txt");
		this.airlines =  Airline.getAirlines("airlines.txt");
	}

	
	public void setUsers(String file){
		//populates the user ArrayList with UserAccounts
		this.users = UserAccount.getUserAccountArray(file);
	}
	
	public void setAirlines(String file){
		//populates the airline ArrayList with Airlines
		this.airlines = Airline.getAirlines(file);
	}
	
	public int userOrAirline(String x){
		//takes in a string to determine what the person wants to log in as
		//1 for User
		//2 for airline
		//returns 0 as a default if they don't enter correctly
		if(x.equalsIgnoreCase("user")) return 1;
		if(x.equalsIgnoreCase("airline")) return 2;
		else return 0;
	}
	
	public boolean isCorrectLogin(int x, String y, String z) {
		//Checks to see what the person wanted to login as
		//then checks to see if the username and password are actually valid
		if(x == 1) {
			for(int i = 0; i < users.size(); i++){
				if(users.get(i).getUsername().equals(y) && users.get(i).getPassword().equals(z)){
					this.currentUser = users.get(i);
					return true;
				}
			}
		}
		if(x == 2) {
			for(int i = 0; i < airlines.size(); i++){
				if(airlines.get(i).getAirlineName().equals(y) && users.get(i).getPassword().equals(z)){
					this.currentAirline = airlines.get(i);
					return true;
				}
			}
		}
		return false; //default if nothing can be returned somehow
	}
	
	public UserAccount getUser() {
		//returns the current user signed in
		return currentUser;
	}
	
	public Airline getAirline() {
		//returns the currently signed in Airline
		return currentAirline;
	}
	
	public void register(int x, String y, String z) {
		//registers either an Airline or UserAccount and their username and password
		if( x == 1) {
			try {
				users.add(new UserAccount(y, z));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if(x == 2) {
			airlines.add(new Airline(y, z));
		}
	}
	
	public ArrayList<Flight> getFlights(String departure, String arrival) {
		//will return flights for the user if they're from and to the user's requested cities.
		//won't return flights if they are full
		ArrayList<Flight> temp = new ArrayList<>();
		// outer loop: cycles through the airlines
		// inner loop: cycles through the flights
		for (int i = 0; i < airlines.size(); i++){
			for (int j = 0; j < airlines.get(i).ourFlights.size(); j++){
				if ((airlines.get(i).ourFlights.get(j).getDeparture().getCity().equals(departure)) && // if the departures match
						(airlines.get(i).ourFlights.get(j).getArrival().getCity().equals(arrival)) && // if the arrivals match
						!airlines.get(i).ourFlights.get(j).isFull()){ // if it is not full
					temp.add(airlines.get(i).ourFlights.get(j));
				}
			}
		}
		return temp;
	}

					 
	public static Flight getFlightOnID(int x){
		ArrayList<Airline> airlines= Airline.getAirlines("airlines.txt");


		for(int i = 0; i < airlines.size(); i++){
			for(int j = 0; j < airlines.get(i).ourFlights.size(); j++){
				if(airlines.get(i).ourFlights.get(j).getId() == x){
					return airlines.get(i).ourFlights.get(j);
				}
			}
		}
		return null; // if it is not found, method returns null
	}
	
	public void setFlight(Flight flight) {
		//takes in whatever Flight the user chooses, and puts that into their UserAccount FlightsTaken ArrayList
		//unless specific UserAccount is blacklisted by that specific airline
		currentUser.addFlight(flight);
	}
	
	public void cancelFlight(Flight flight) {
		//removes the selected Flight from the FlightsTaken ArrayList
		//as long as the selected Flight can be canceled.
		//reimburses UserAccount with appropriate miles.
		ArrayList<Integer> temp = currentUser.getFlightsTaken();
		for(int i = 0; i < temp.size(); i++){
			if(temp.get(i) == flight.getId()){
				currentUser.getFlightsTaken().remove(i);
				break;
			}
		}
	}
	
	public static String showFlightInfo(Flight flight) {
		//shows all of the information for given flight, including layovers, ticket price, available seats, etc.
		boolean[][] seats = flight.getSeats();
		String empty = "";
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; i < seats[i].length; j++) {
				if (seats[i][j] == false) {
					empty = empty + "Row: " + i + " Seat: " + ((char)((int)'a' + j)) + ", ";
				}
			}
		}
		String result = "Flight Number " + flight.getId() + "\n"
				+ " for " + flight.getArrival() + " from " + flight.getDeparture() + "\n"
				+ " with a base cost of " + flight.getBasePrice() + "\n"
				+ " and has seats at " + empty;
		return result;
	}
	
	public void payment(String payment) {
		//checks to see how the user wants to pay
		//if with miles it will check to see if they have enough
		if(payment.equals("miles") || payment.equals("Miles")) {
			if(currentUser.getMiles() >= 0) { //arbitrary number is arbitrary
				System.out.println("Thank you! The miles have been subtracted from your account");
			}
			else {
				System.out.println("Sorry, but you don't have enough miles. Please pay with card instead");
				Scanner sc = new Scanner(System.in);
				System.out.println("Please Enter Card Number: ");
				String x = sc.nextLine();
				System.out.println("Please Enter Expiration Date: ");
				x = sc.nextLine();
				System.out.println("Please Enter CVN (Three Numbers On The Back): ");
				x = sc.nextLine();
				System.out.println("Thank you! The amount has been billed to your card");
				sc.close();
			}
		}
		//if with card, will ask for arbitrary card numbers, won't store any of the card numbers. Since payment isn't actually happening.
		if(payment.equals("card") || payment.equals("Card")) {
			Scanner sc = new Scanner(System.in);
			System.out.println("Please Enter Card Number: ");
			String x = sc.nextLine();
			System.out.println("Please Enter Expiration Date: ");
			x = sc.nextLine();
			System.out.println("Please Enter CVN (Three Numbers On The Back): ");
			x = sc.nextLine();
			System.out.println("Thank you! The amount has been billed to your card");
			sc.close();
		}
	}
	
	public void rate(Review review) {
		//adds the specific review to the reviews of the specific airline from the Review constructor
		//Only lets the user do this if the flight is in their FlightsTaken ArrayList
		if(currentUser.getFlightsTaken().contains(review.getFlight().getId())){
			review.getFlight().getAirline().ourReviews.add(review);
		}
	}
	
	public void orderFlight() {
		Scanner sc = new Scanner(System.in);
		System.out.println("What city will you be departing from?");
		String y = sc.nextLine();
		System.out.println("What city do you want to arrive at?");
		String z = sc.nextLine();
		
		for (int i = 0; i < getFlights(y,z).size(); i++) {
			if (getFlights(y,z).get(i).isFull() == false) {
				System.out.println("Flight ID: " + this.getFlights(y,z).get(i).getId());
				System.out.println("Number of planes to board to get to destination: " + (this.getFlights(y,z).get(i).getRoute().size() - 1));
			}
		}
		
		System.out.println("These are all flights which have your desired departure and arrival.");
		
		System.out.println("Enter an ID number to find the price, then enter 'confirm' to move to payment.");
		
		System.out.println("Or enter 'cancel' to look at the list again.");
		
		String answer = sc.nextLine();
		
		while(!answer.equalsIgnoreCase("confirm")) {
				System.out.println("Please enter a flight ID");
				int id = sc.nextInt();
				Flight temp = getFlightOnID(id);
				System.out.println(showFlightInfo(temp));
				System.out.println("Is this the flight you want to book?");
				answer = sc.nextLine();
				if(answer.equalsIgnoreCase("cancel")){
					for(int i = 0; i < getFlights(y,z).size(); i++){
						System.out.println(getFlights(y,z).get(i).getId());
					}
				}
				else if(answer.equalsIgnoreCase("confirm")){
				    System.out.println("Would you like chicken or beef for your meal?");
				    String choice = sc.nextLine(); // lol
					System.out.println("How would you like to pay? Card or miles?");
					String pay = sc.nextLine();
					payment(pay);
					setFlight(temp);
					System.out.println("Flight successfully added");
				}
		}
		sc.close();
	}
	
	public void cancelFlight() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Do you know the ID of the flight you wish to cancel?");
		String x = sc.nextLine();
		if(x.equalsIgnoreCase("yes")) {
			System.out.println("Great! Please enter the ID!");
			int y = sc.nextInt();
			Flight thisFlight = getFlightOnID(y);
			if (thisFlight.getDeparture().getTime().isAfter(LocalDateTime.now())){
				cancelFlight(thisFlight);
			}
			else {
				System.out.println("You cannot cancel flights that have already happened.");
			}
		}
		else {
			System.out.println("Ok! Here's the list of flights you can cancel!");
			for(int i = 0; i < currentUser.getFlightsTaken().size(); i++){

				Flight currentFlight = getFlightOnID(currentUser.getFlightsTaken().get(i));
					if (currentFlight.getDeparture().getTime().isAfter(LocalDateTime.now())) {
						System.out.println(currentFlight.getId());
						System.out.println(currentFlight.getDeparture());
						System.out.println(currentFlight.getArrival());
					}
			}
			System.out.println("Select the ID number of the flight you wish to cancel.");
			int y = sc.nextInt();
			cancelFlight(getFlightOnID(y));
			System.out.println("Flight successfully canceled!");
			currentUser.addMiles(500); //arbitrary number is arbitrary
		}
		sc.close();
	}
	
	public void reviewFlight() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Which of your flights would you like to review? (Please select the ID)");
		for(int i = 0; i < currentUser.getFlightsTaken().size(); i++){
			Flight currentFlight = getFlightOnID(currentUser.getFlightsTaken().get(i));
			System.out.println(currentFlight.getId());
			System.out.println(currentFlight.getDeparture());
			System.out.println(currentFlight.getArrival());
		}
		
		int y = sc.nextInt();
		Flight tempFlight = getFlightOnID(y);
		System.out.println("Out of 5 stars, how would you rate your trip?");
		int rating = sc.nextInt();
		System.out.println("Would you like to say anything about how the trip went?");
		String review = "";
		// Makes sure review is actually initialized for the review object.
		review = sc.nextLine();
		System.out.println("Thank you for your review!");
		try {
			rate(new Review(rating, review, currentUser, tempFlight));
		} catch (Exception e) {
			e.printStackTrace(); // exception for when the currently logged in user can't review the flight (eg didnt take it)
		}
		sc.close();
	}
	
	public void addFlight() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Okay, what should the ID number of this flight be?");
		int id = sc.nextInt();
		System.out.println("Where does this flight depart from?");
		String departureCity = sc.nextLine();
		System.out.println("What is the latitude of that city?");
		int latitude1 = sc.nextInt();
		System.out.println("What is the longitude of that city?");
		int longitude1 = sc.nextInt();
		System.out.println("where does this flight arrive at?");
		String arrivalCity = sc.nextLine();
		System.out.println("What is the latitude of that city?");
		int latitude2 = sc.nextInt();
		System.out.println("What is the longitude of that city?");
		int longitude2 = sc.nextInt();
		System.out.println("When does this flight take off? Give in year, month, day, hour, and minute in integers, pressing enter between each number.");
		int year = sc.nextInt();
		int month = sc.nextInt();
		int day = sc.nextInt();
		int hour = sc.nextInt();
		int minute = sc.nextInt();
		System.out.println("How long will the flight be, in hours?");
		long flightHours = sc.nextInt();
		LocalDateTime departureTime = LocalDateTime.of(year, month, day, hour, minute);
		FlightStop departure = new FlightStop(latitude1, longitude1, departureCity, departureTime);
		FlightStop arrival = new FlightStop(latitude2, longitude2, arrivalCity, departureTime.plusHours(flightHours));
		ArrayList<FlightStop> temp = new ArrayList<>();
		temp.add(departure);
		
		
		System.out.println("Any layovers?");
		String answer = sc.nextLine();
		if (answer.equalsIgnoreCase("yes")) {
			String done = sc.nextLine();
			while (!done.equalsIgnoreCase("done")) {
				System.out.println("Where is this layover?");
				String layoverCity = sc.nextLine();
				System.out.println("What is the latitude of that city?");
				int latitude = sc.nextInt();
				System.out.println("What is the longitude of that city?");
				int longitude = sc.nextInt();
				System.out.println("When does this flight take off? Give in year, month, day, hour, and minute in integers, pressing enter between each number.");
				year = sc.nextInt();
				month = sc.nextInt();
				day = sc.nextInt();
				hour = sc.nextInt();
				minute = sc.nextInt();
				
				LocalDateTime layoverTime = LocalDateTime.of(year, month, day, hour, minute);
				FlightStop layover = new FlightStop(latitude, longitude, layoverCity, layoverTime);

				temp.add(layover);
				System.out.println("Done? Type 'done' if so, otherwise type no and add another layover.");
				done = sc.nextLine();
			}
		}
		
		
		temp.add(arrival);
		Flight flight = new Flight(id, this.currentAirline, temp, 100, 20, 20, 6, departureTime);
		System.out.println("Successfully added flight!");
		this.currentAirline.addFlights(flight);
		
		sc.close();
	}
	
	public void cancelFlight2() {
		Scanner sc = new Scanner(System.in);
		System.out.println("What's the ID number of the flight you want to cancel?");
		int y = sc.nextInt();
		for(int i = 0; i < currentAirline.ourFlights.size(); i++){
			if(currentAirline.ourFlights.get(i).getId() == y){
				for(int j = 0; j < users.size(); j++){
					if(users.get(j).getFlightsTaken().contains(currentAirline.ourFlights.get(i).getId())){
						giftMiles(users.get(j), getFlightOnID(currentAirline.ourFlights.get(i).getId()).getBasePrice() - 200);
					}
				}
				removeFlights(currentAirline.ourFlights.get(i));
				System.out.println("Successfully removed");
			}
		}
		sc.close();
	}
	
	
	public void getReviews(Airline airline) {
		//if the login was by an Airline, the reviews only pop in for their Airline
		//gets reviews for the specified airline
		airline.viewReviews();
	}
	
	public void addFlight(Flight flight) {
		//adds the specific flight to the airlines options
		currentAirline.addFlights(flight);
	}
	
	public void removeFlights(Flight flight) {
		//removes the specific flight from the airlines options if it exists
		currentAirline.removeFlight(flight);
	}
	
	public void giftMiles(UserAccount x, int howManyMiles) {
		//gifts miles to certain users if they've been good boys or girls all year long
		currentAirline.giveMiles(x, howManyMiles);
	}
	
	public void addBlacklist(UserAccount x) {
		//blacklists the specified UserAccount from booking on their Flights
		currentAirline.addNoFly(x);
	}
	
	
	public void wholeProgramDriver() {
		//Where all of the magic will happen when someone wants to login and do what they want.
		setUsers("userAccounts.txt");
		setAirlines("airlines.txt");
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Hello there! Do you wish to login as a user or as an airline?");
		String type = sc.nextLine();
		System.out.println("Very good! What's your username?");
		String username = sc.nextLine();
		System.out.println("And your password?");
		String password = sc.nextLine();
		
		
		if((isCorrectLogin(userOrAirline(type), username, password))){
			System.out.println("Very good! Logging you in...");
			
			if(userOrAirline(type) == 1){ // 1 = user
				
				//USER EXPERIENCE

				System.out.println("What would you like to do?");
				String option = sc.nextLine();
				while(!option.equalsIgnoreCase("logout")){
					System.out.println("Options:" + "\n"
						   + "- Order flight"  + "\n"
						   + "- Cancel flight" + "\n"
						   + "- Review flight" + "\n"
						   + "- Logout");
					option = sc.nextLine();
					
					if (option.equalsIgnoreCase("order flight")) {
						orderFlight();
					}
					
					if (option.equalsIgnoreCase("cancel flight")) {
						cancelFlight();
					}	
					
					if (option.equalsIgnoreCase("review flight")) {
						reviewFlight();
					}
	
			else if (userOrAirline(type) == 2) { // 2 = airline
				
				//AIRLINE EXPERIENCE
				
				System.out.println("What would you like to do?");
				System.out.println("To logout, type 'logout', to see the options, press any key");
				String option2 = sc.nextLine();
				do{
					System.out.println("Options:" + "\n"
						   + "Add flight"  + "\n"
						   + "Cancel flight" + "\n"
						   + "See reviews" + "\n"
						   + "Gift miles" + "\n"
						   + "Add to blacklist" + "\n"
						   + "Logout");
					option2 = sc.nextLine();
					
					if (option2.equalsIgnoreCase("add flight")) {
						addFlight();
					}
					
					if (option2.equalsIgnoreCase("cancel flight")){
						cancelFlight2();
					}
					
					if (option2.equalsIgnoreCase("gift miles")) {
						System.out.println("What's the username of the person you want to gift miles to?");
						String y = sc.nextLine();
						for(int i = 0; i < users.size(); i++){
							if(users.get(i).getUsername() == y){
								giftMiles(users.get(i), 500);// gifts 500 miles
								System.out.println("Successfully gifted miles!");
							}
							else {
								System.out.println("Could not find anyone with that username");	
							}
						}
					}
					
					if (option2.equalsIgnoreCase("add to blacklist")) {
						System.out.println("What's the username of the person you want to blacklist?");
						String y = sc.nextLine();
						for(int i = 0; i < users.size(); i++){
							if(users.get(i).getUsername() == y){
								addBlacklist(users.get(i));
								System.out.println("Successfully blacklisted!");
							}
							else{
								System.out.println("Could not find anyone with that username");	
							}
						}
					}
					
					if(option2.equalsIgnoreCase("see reviews")) {
						getReviews(currentAirline);
					}
					
				} while (!option2.equalsIgnoreCase("logout"));
			}
				}
			}
		}

		
		
		else {
			System.out.println("That username and/or password does not exist.");
			System.out.println("Would you like to register instead? Yes or No.");
			String ans = sc.nextLine();
			if(ans.equalsIgnoreCase("yes")){
				System.out.println("Registering a user or an Airline?");
				String ans2 = sc.nextLine();
				if(ans2.equalsIgnoreCase("user")){
					System.out.println("What do you want your username to be?");
					String u = sc.nextLine();
					System.out.println("What do you want your password to be?");
					String p = sc.nextLine();
					register(1, u, p);
					System.out.println("Restart to log in.");
				}
				else{
					System.out.println("What do you want your username to be?");
					String u = sc.nextLine();
					System.out.println("What do you want your password to be?");
					String p = sc.nextLine();
					register(2, u, p);
					System.out.println("Restart to log in.");
				}
			}
		}
		setUsers("userAccounts.txt");
		setAirlines("Airlines.txt");
		sc.close();
	}




	public static void main(String[] args){
	    Airline testAirline = new Airline("airline", "test");
		UserAirlineInterface thisProgram = new UserAirlineInterface();
		thisProgram.airlines.add(testAirline);
		thisProgram.wholeProgramDriver();
	}
}
