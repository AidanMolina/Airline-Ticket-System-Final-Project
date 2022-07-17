import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class UserAccount {

    private String username;
    private String password;
    private int miles;
    private ArrayList<Integer> flightsTaken; // this contains only the flight id number
    private ArrayList<Ticket> tickets;
    private boolean paidForClassUpgrade;
    private boolean paidForSeatChange;

    // no setters for username/password because they cannot be changed after creating an account
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public int getMiles() {
        return miles;
    }


    public ArrayList<Ticket> getTickets() {
        return tickets;
    }

    public void addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        try {
            File file = new File(username+"Tickets.txt");
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            pr.println(ticket.price+" "+Boolean.toString(ticket.isFirstClass)+" "+ticket.getFlight().getId()+" "+ticket.dateBooked);
            pr.close();
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void addFlight(Flight flight){
        // adds flight to the flightsTaken ArrayList
        this.flightsTaken.add(flight.getId());
        try {
            File file = new File(username+"Flights.txt");
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            pr.println(flight.getId());
            pr.close();
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Integer> getFlightsTaken() {
        return flightsTaken;
    }

    public static ArrayList<UserAccount> getUserAccountArray(String fileName){
        ArrayList<UserAccount> users = new ArrayList<>();
        String row = "";
        try {
            BufferedReader userReader = new BufferedReader(new FileReader(fileName));
            while ((row = userReader.readLine()) != null){ // while there are still lines left in the file
                String[] thisUserStrings = row.split(" "); // parse the row into an array of strings
                UserAccount thisUser = new UserAccount(thisUserStrings[0], thisUserStrings[1], Integer.parseInt(thisUserStrings[2])); // string 0 = username, string 1 = pw, string 2 = miles
                users.add(thisUser);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public UserAccount(String username, String password, int miles){
        this.username = username;
        this.password = password;
        this.miles = miles;
        this.flightsTaken = new ArrayList<>();
        this.tickets = new ArrayList<>();
        // TODO get the person's flights and tickets from the txt files.
        try {
            File flights = new File(username+"Flights.txt");
            flights.createNewFile();
            BufferedReader flightReader = new BufferedReader(new FileReader(flights));
            String row = "";
            while ((row = flightReader.readLine())!=null){
                int thisFlightId = Integer.parseInt(row);
                flightsTaken.add(thisFlightId);
            }
            flightReader.close();

            File tickets = new File(username+"Tickets.txt");
            tickets.createNewFile();
            BufferedReader ticketReader = new BufferedReader(new FileReader(tickets));
            String ticketRow = "";
            while ((ticketRow = ticketReader.readLine())!=null){
                String[] thisTicket = ticketRow.split(" ");
                int price = Integer.parseInt(thisTicket[0]);
                boolean isFirstClass = Boolean.getBoolean(thisTicket[1]);
                Flight thisFlight = UserAirlineInterface.getFlightOnID(Integer.parseInt(thisTicket[2]));
                int seatRow = Integer.parseInt(thisTicket[3]);
                char whichSeatInRow = thisTicket[4].charAt(1);
                LocalDateTime timeBooked = LocalDateTime.parse(thisTicket[5]);
      
                Ticket newTicket = new Ticket(thisFlight, timeBooked, isFirstClass, seatRow, whichSeatInRow);
                this.tickets.add(newTicket);
            }
            ticketReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserAccount(String username, String password) throws RuntimeException, IOException {
        ArrayList<UserAccount> users = getUserAccountArray("userAccounts.txt");
        for (int i = 0; i < users.size(); i++){
            if (users.get(i).username.equals(username)){
                throw new RuntimeException("Username " +username+" already taken");
            }
        }
            this.username = username;
            this.password = password;
            this.miles = 0;
            this.flightsTaken = new ArrayList<>();
            this.tickets = new ArrayList<>();
            // this creates a new text file that will contain the user's flights
            // as the person hasn't taken any flights yet and doesn't have any tickets yet, the files will be empty.

            File userFlights = new File(username+"Flights.txt");
            userFlights.createNewFile();
            File userTickets = new File(username+"Tickets.txt");
            userTickets.createNewFile();

        try {
            //this writes to the userAccounts file with the person's login info
            File file = new File("userAccounts.txt");
            FileWriter fr = new FileWriter(file, true);
            BufferedWriter br = new BufferedWriter(fr);
            PrintWriter pr = new PrintWriter(br);
            pr.println(username+" "+password+" "+ 0);
            pr.close();
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // maybe create custom exception??
    }

    // tells if the given username and password correspond to the account
    public boolean thisUser(String testUsername, String testPassword){
        return ((testUsername.equals(this.username))&&(testPassword.equals(this.password)));
    }

    // this will set the miles (like if an airline gifted someone miles)
    public void addMiles(int newMiles){
        this.miles += newMiles;
        File users = new File("userAccounts.txt");
        String newFileContents = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(users));
            String line = "";
            while ((line = reader.readLine())!=null){
                String[] thisLine = line.split(" ");
                // if the username matches, update the miles.
                if(thisLine[0].equals(this.username)){
                    thisLine[2] = ""+this.miles;
                }
                String updatedLine = String.join(" ", thisLine);
                newFileContents = newFileContents + updatedLine + "\n";
            }
            FileWriter fileWriter = new FileWriter(users);
            fileWriter.write(newFileContents);
            reader.close();
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // this converts a UserAccount to a string for testing purposes
    public String toString(){
        return this.username+" "+this.password+" "+this.miles;
    }

	public boolean isPaidForClassUpgrade() {
		return paidForClassUpgrade;
	}

	public void setPaidForClassUpgrade(boolean paidForClassUpgrade) {
		this.paidForClassUpgrade = paidForClassUpgrade;
	}

	public boolean isPaidForSeatChange() {
		return paidForSeatChange;
	}

	public void setPaidForSeatChange(boolean paidForSeatChange) {
		this.paidForSeatChange = paidForSeatChange;
	}

    

}
