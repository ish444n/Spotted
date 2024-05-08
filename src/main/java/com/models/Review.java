package com.models;

public class Review {
	private int reviewID;
	private int userID;
    private int locationID;
    private String details;
    
    public Review(int reviewID, int userID, int locationID, String details) {
        this.reviewID = reviewID;
    	this.userID = userID;
        this.locationID = locationID;
        this.details = details;
    } 
    
    public int getUserID() {
    	return userID;
    }
    
    public int getLocationID () {
    	return locationID;
    }
    
    public String getDetails () {
    	return details;
    }
    
}
