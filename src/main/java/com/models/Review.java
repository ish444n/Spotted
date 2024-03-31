
public class Review {
	private int reviewID;
	private int userID;
    private int locationID;
    private double starRating;
    private String details;
    
    public Review(int reviewID, int userID, int locationID, double starRating, String details) {
        this.reviewID = reviewID;
    	this.userID = userID;
        this.locationID = locationID;
        this.starRating = starRating;
        this.details = details;
    } 
    
    public double getStarRating () {
    	return starRating;
    }
    /*
    public void setDetail () {
    	
    }
    
    public void setStarRating () {
    	
    }
    */
}
