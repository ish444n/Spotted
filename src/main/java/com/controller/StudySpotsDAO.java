package com.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudySpotsDAO {
    
    // Database connection URL
    private static final String DB_URL = "jdbc:mysql://localhost:3306/StudySpots?user=root&password=root";

    // Method to print the contents of the ReviewTable
    public static void printReviews() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
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

    // Method to print the contents of the UserTable
    public static void printUsers() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
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
        try (Connection conn = DriverManager.getConnection(DB_URL);
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
        try (Connection conn = DriverManager.getConnection(DB_URL);
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
        try (Connection conn = DriverManager.getConnection(DB_URL);
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

 // Method to print the contents of the BookmarkedSpotsTable
    public static void printBookmarkedSpots() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM BookmarkedSpotsTable")) {
            
            System.out.println("\nBookmarkedSpotsTable:");
            System.out.println("UserID\tStudySpotID");
            while (rs.next()) {
                int userID = rs.getInt("UserID");
                int studySpotID = rs.getInt("StudySpotID");
                System.out.println(userID + "\t" + studySpotID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void printUserPreferences() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM UserPreferencesTable")) {
            
            System.out.println("\nUserPreferencesTable:");
            System.out.println("userId\tcomfortAndConvenience\tConnectivity\tpreferredStudySpaceSize\tnoiseLevel\tprefers24_7");
            while (rs.next()) {
                int userId = rs.getInt("userId");
                String comfortAndConvenience = rs.getString("comfortAndConvenience");
                boolean connectivity = rs.getBoolean("Connectivity");
                String preferredStudySpaceSize = rs.getString("preferredStudySpaceSize");
                String noiseLevel = rs.getString("noiseLevel");
                boolean prefers24_7 = rs.getBoolean("prefers24_7");
                System.out.println(userId + "\t" + comfortAndConvenience + "\t" + connectivity + "\t" + preferredStudySpaceSize + "\t" + noiseLevel + "\t" + prefers24_7);
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
        printBookmarkedSpots();
        printUserPreferences();
    }
}
