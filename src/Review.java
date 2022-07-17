public class Review {
    private int rating; // rating out of 5 stars
    private String reviewContents; // string containing the review comments
    private UserAccount reviewer; // person that made the review
    private Flight flight; // flight that the review is about

    public Review(int rating, String reviewContents, UserAccount reviewer, Flight flight) throws Exception {
        this.rating = rating;
        this.flight = flight;
        this.reviewContents = reviewContents;
        this.reviewer = reviewer;
        if (!(this.reviewer.getFlightsTaken().contains(flight))){
            throw new Exception("You cannot review flights that you have not taken");
            // If the user writing the review didn't take the flight, throw an exception
        }
        else{
            flight.getAirline().ourReviews.add(this);
        }
    }

    public int getRating() {
        return rating;
    }

    public Flight getFlight() {
        return flight;
    }

    public String getReviewContents() {
        return reviewContents;
    }

    public UserAccount getReviewer() {
        return reviewer;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReviewContents(String reviewContents) {
        this.reviewContents = reviewContents;
    }

    public void setReviewer(UserAccount reviewer) {
        this.reviewer = reviewer;
    }
}
