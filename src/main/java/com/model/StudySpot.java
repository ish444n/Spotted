import java.awt.Image;
import java.util.List;

public class StudySpot {
	private int LocationID;
	private String name;
   // private String location;
    private String description;
    private Specification specs;
    private List<Review> reviews;
    private List<Image> pictures;
    private double avgRating = -1;
    
    public StudySpot(int LocationID, String name, String description, Specification specs, List<Review> reviews, List<Image> pictures) {
        this.LocationID = LocationID;
    	this.name = name;
      //  this.location = location;
        this.description = description;
        this.specs = specs;
        this.reviews = reviews;
        this.pictures = pictures;
        updateAvgRating(false);
    }
    
    // servlet
    /*
    public void addReview(Review r) {
    	reviews.add(r);
    	updateAvgRating(true);
    }
    */
    
    // servlet class should call this with true
    public void updateAvgRating(boolean isUpdate) {
    	
    	if (isUpdate && reviews.size() != 1) 
    	{
    		int past_size = reviews.size() - 1;
    		avgRating = ((avgRating * past_size) + reviews.get(past_size - 1).getStarRating())/reviews.size();
    	}
    	else if (!reviews.isEmpty()) { // loop thru reviews when it's the 1st time calculating avg rating
    		for (int i = 0; i < reviews.size(); ++i) {
    			avgRating += reviews.get(i).getStarRating();
    		}
    		avgRating /= reviews.size();
    	}
    }
    
    public int getLocationID() {
        return LocationID;
    }

    public String getName() {
        return name;
    }

    /*
    public String getLocation() {
        return location;
    }
     */
    public String getDescription() {
        return description;
    }

    public Specification getSpecs() {
        return specs;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public List<Image> getPictures() {
        return pictures;
    }

    public double getAvgRating() {
        return avgRating;
    }
    
    

}
