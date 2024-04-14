package com.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import com.models.*;

public class StudySpotsDAO {
	
	// Database Connection URL
	private static final String DB_URL = "jdbc:mysql://localhost:3306/StudySpots?user=root&password=root";
    
    // Returns the Database Connection object
	public static Connection getConnection() throws SQLException {
		try {
			Connection conn = DriverManager.getConnection(DB_URL);
			return conn;
		} catch(SQLException sqle) {
			System.out.println("Fatal! Error in connecting to DataBase!");
			System.out.println(sqle.getMessage());
			throw sqle;
		}
	}
	
	// gets all bookmarked studySpots for a given userID
	public static List<StudySpot> getUserBookmarked(String userID) {
	    List<StudySpot> bookmarkedSpots = new Vector<StudySpot>();
	    
	    // set JDBC vars
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	        conn = getConnection();
	        String sql = "SELECT s.LocationID, s.Name, s.Description, s.SpecID " +
	                     "FROM StudySpotsTable s " +
	                     "JOIN BookmarkedSpots b ON s.LocationID = b.LocationID " +
	                     "WHERE b.UserID = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, userID);
	        
	        // execute & get results
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	            // get the study spot params to build the study spot
	            int locationID = rs.getInt("LocationID");
	            String name = rs.getString("Name");
	            String description = rs.getString("Description");
	            int specID = rs.getInt("SpecID");
	            
	            // Retrieve specifications, reviews, and pictures for the study spot
	            Specification specs = getSpecifications(specID); 
	            List<Review> reviews = getReviews(locationID); 
	            List<Image> pictures = getStudySpotImages(locationID);
	            
	            // Assuming that you have a constructor in your StudySpot class like StudySpot(int locationID, String name, String description, Specification specs, List<Review> reviews, List<Image> pictures)
	            StudySpot spot = new StudySpot(locationID, name, description, specs, reviews, pictures);
	            bookmarkedSpots.add(spot);
	        }	
	    } catch (SQLException e) {
	        System.out.println("Error in connecting to database.");
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return bookmarkedSpots;
	}

	// given a studySpotID, returns the list of all images
	public static List<Image> getStudySpotImages(int locationID) {
		List<Image> images = new Vector<Image>();
		
		// set JDBC vars
		Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
		
		try {
			conn = getConnection();
	        String sql = "SELECT * FROM ImagesTable WHERE LocationID = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, locationID);
	        
	        // execute & get results
	        rs = stmt.executeQuery();
	        while (rs.next()) {
	        	// get image information
	        	String imagePath = rs.getString("ImagePath");
	        	String imageID = rs.getString("ImageID");
	        	
	        	// create image from the details and add it to the going list
	        	Image currImage = new Image(imagePath, Integer.parseInt(imageID), locationID);
	        	images.add(currImage);
	        }
		} catch (NumberFormatException nfe){
			System.out.println("NumberFormatException in getStudySpotImages: " + nfe.getMessage());
		} catch (SQLException e) {
			System.out.println("Error in connecting to database.");
			e.printStackTrace();
		} finally {
			try {
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
		return images;
	}

    // Method to print the contents of the ReviewTable
    public static void printReviews() {
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM ReviewTable")) {
            
            System.out.println("\nReviewTable:");
            System.out.println("ReviewID\tUserID\tLocationID\tStarRating\tDetails");
            while (rs.next()) {
                int reviewID = rs.getInt("ReviewID");
                int userID = rs.getInt("UserID");
                int locationID = rs.getInt("LocationID");
                int starRating = rs.getInt("StarRating");
                String details = rs.getString("Details");
                System.out.println(reviewID + "\t" + userID + "\t" + locationID + "\t" + starRating + "\t" + details);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // returns a list of specifications for a given specID
    public static Specification getSpecifications(int specID) {
        Specification specs = null;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM SpecsTable WHERE SpecID = ?")) {
            
            stmt.setInt(1, specID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int waterFountains = rs.getInt("waterFountain");
                    int restrooms = rs.getInt("restroom");
                    int microwaves = rs.getInt("Microwaves");
                    int refrigerators = rs.getInt("refrigerators");
                    int outlets = rs.getInt("outlets");
                    int AC = rs.getInt("AC");
                    int WiFi = rs.getInt("WiFi");
                    int SeatingCapacity = rs.getInt("SeatingCapacity");
                    int noiseLevel = rs.getInt("noiseLevel");
                    String openingHours = rs.getString("openingHours"); // This column must exist in your SpecsTable

                    specs = new Specification(specID, waterFountains, restrooms, microwaves, refrigerators, outlets, AC, WiFi, SeatingCapacity, noiseLevel, openingHours);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException in getSpecifications: " + e.getMessage());
            e.printStackTrace();
        }

        return specs;
    }

    
    // returns the list of reviews for a study spot given a locationID
    public static List<Review> getReviews(int locationID) {
        List<Review> reviews = new Vector<Review>();

        // open connection
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ReviewTable WHERE LocationID = ?")) {
            
            stmt.setInt(1, locationID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int reviewID = rs.getInt("ReviewID");
                    int userID = rs.getInt("UserID");
                    double starRating = rs.getDouble("StarRating");
                    String details = rs.getString("Details");

                    Review review = new Review(reviewID, userID, locationID, starRating, details);
                    reviews.add(review);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException in getReviews: " + e.getMessage());
            e.printStackTrace();
        }

        return reviews;
    }

    // Method to print the contents of the UserTable
    public static void printUsers() {
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM UserTable")) {
            
            System.out.println("UserTable:");
            System.out.println("UserID\tUsername\tPassword\tEmail\tBio\tImage");
            while (rs.next()) {
                int userID = rs.getInt("UserID");
                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String email = rs.getString("Email");
                String bio = rs.getString("Bio");
                String image = rs.getString("Image");
                System.out.println(userID + "\t" + username + "\t" + password + "\t" + email + "\t" + bio + "\t" + image);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to print the contents of the SpecsTable
    public static void printSpecs() {
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM SpecsTable")) {
            
            System.out.println("\nSpecsTable:");
            System.out.println("specID\twaterFountain\trestroom\tMicrowaves\trefrigerators\toutlets\tac\tWiFi\tSeatingCapacity\tnoiseLevel");
            while (rs.next()) {
                int specID = rs.getInt("specID");
                boolean waterFountain = rs.getBoolean("waterFountain");
                boolean restroom = rs.getBoolean("restroom");
                boolean microwaves = rs.getBoolean("Microwaves");
                boolean refrigerators = rs.getBoolean("refrigerators");
                boolean outlets = rs.getBoolean("outlets");
                boolean ac = rs.getBoolean("ac");
                boolean wifi = rs.getBoolean("WiFi");
                int seatingCapacity = rs.getInt("SeatingCapacity");
                String noiseLevel = rs.getString("noiseLevel");
                System.out.println(specID + "\t" + waterFountain + "\t" + restroom + "\t" + microwaves + "\t" + refrigerators + "\t" + outlets + "\t" + ac + "\t" + wifi + "\t" + seatingCapacity + "\t" + noiseLevel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to print the contents of the ImagesTable
    public static void printImages() {
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM ImagesTable")) {
            
            System.out.println("\nImagesTable:");
            System.out.println("ImageID\tLocationID\tImagePath\tUpvotes\tDownvotes");
            while (rs.next()) {
                int imageID = rs.getInt("ImageID");
                int locationID = rs.getInt("LocationID");
                String imagePath = rs.getString("ImagePath");
                int upvotes = rs.getInt("Upvotes");
                int downvotes = rs.getInt("Downvotes");
                System.out.println(imageID + "\t" + locationID + "\t" + imagePath + "\t" + upvotes + "\t" + downvotes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to print the contents of the StudySpotsTable
    public static void printStudySpots() {
        try (Connection conn = getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM StudySpotsTable")) {
            
            System.out.println("\nStudySpotsTable:");
            System.out.println("LocationID\tName\tDescription\tSpecID\tImagesID");
            while (rs.next()) {
                int locationID = rs.getInt("LocationID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int specID = rs.getInt("SpecID");
                int imagesID = rs.getInt("ImagesID");
                System.out.println(locationID + "\t" + name + "\t" + description + "\t" + specID + "\t" + imagesID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        printUsers();
        printSpecs();
        printImages();
        printStudySpots();
        printReviews(); 
    }
}