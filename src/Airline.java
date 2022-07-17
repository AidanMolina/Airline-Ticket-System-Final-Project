import java.io.BufferedReader;
import java.util.*;
import java.io.FileReader;
import java.io.*;

public class Airline {
	public ArrayList<Flight> ourFlights;
	public ArrayList<UserAccount> noFlyList;
	public ArrayList<Review> ourReviews;
	public ArrayList<Airline> airlines;
	public String airlineName;
	public String password;
	
	//constructor
	public Airline (String name, String loginPassword) {
		ArrayList<Airline> alreadyTakenNames = Airline.getAirlines("airlines.txt");
		if (airlines!=null) {
			for (int i = 0; i < airlines.size(); i++) {
				if (airlines.get(i).getAirlineName().equals(name)) {
					throw new RuntimeException("Airline name " + name + " already taken");
				}
			}
		}
		this.airlineName = name;
		this.password= loginPassword;
		this.ourFlights = new ArrayList<>();
		this.noFlyList = new ArrayList<>();
		
		try {
          	  //this writes to the userAccounts file with the person's login info
           	File file = new File("airlines.txt");
            	FileWriter fr = new FileWriter(file, true);
            	BufferedWriter br = new BufferedWriter(fr);
            	PrintWriter pr = new PrintWriter(br);
            	pr.println(name+" "+loginPassword);
            	pr.close();
            	br.close();
            	fr.close();
        	} catch (IOException e) {
            	e.printStackTrace();
        	}
	}
	
	//add flights
	public void addFlights (Flight newFlight) {
		ourFlights.add(newFlight);
	}
	
	//removes a flight
	public void removeFlight (Flight removedFlight) {
		if (ourFlights.contains(removedFlight))
		{
			for (int i = 0; i < ourFlights.size(); i++)
			{
				if (ourFlights.get(i) == removedFlight)
					ourFlights.remove(i);
			}
		}
	}
	
	//add review
	public void addReview(Review ourReview){
		ourReviews.add(ourReview);
	}
	
	//add to nofly list
	public void addNoFly(UserAccount user) {
		noFlyList.add(user);
	}
	
	//view reviews for airline
	public void viewReviews() {
		System.out.print(ourReviews);
	}
	
	//award passengers with frequent flier miles
	public void giveMiles (UserAccount user, int numMiles) {
		user.addMiles(user.getMiles() + numMiles);
	}


	public ArrayList<Flight> getOurFlights() {
		return ourFlights;
	}


	public void setOurFlights(ArrayList<Flight> ourFlights) {
		this.ourFlights = ourFlights;
	}


	public ArrayList<UserAccount> getNoFlyList() {
		return noFlyList;
	}


	public void setNoFlyList(ArrayList<UserAccount> noFlyList) {
		this.noFlyList = noFlyList;
	}


	public ArrayList<Review> getOurReviews() {
		return ourReviews;
	}


	public void setOurReviews(ArrayList<Review> ourReviews) {
		this.ourReviews = ourReviews;
	}


	public String getAirlineName() {
		return airlineName;
	}


	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	public static ArrayList<Airline> getAirlines(String fileName) {
		ArrayList<Airline> airlines = new ArrayList<>();
		String row = "";
		try {
		    BufferedReader userReader = new BufferedReader(new FileReader(fileName));
		    while ((row = userReader.readLine()) != null){ // while there are still lines left in the file
			String[] thisUserStrings = row.split(" "); // parse the row into an array of strings
			Airline
					thisAirline =
					new Airline(thisUserStrings[0], thisUserStrings[1]); // string 0 = username, string 1 = pw, string 2 = miles
			airlines.add(thisAirline);
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return airlines;
	}
	
}
