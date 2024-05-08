package com.models;

import java.util.List;

public class StudySpot {
	private int LocationID;
	private String name;
    private String description;
    private Specification specs;
    private List<Review> reviews;
    private List<Image> pictures;
    private double latitude;
    private double longitude;
    
    public StudySpot(int LocationID, String name, String description, Specification specs, List<Review> reviews, List<Image> pictures, double latitude, double longitude) {
        this.LocationID = LocationID;
    	this.name = name;
        this.description = description;
        this.specs = specs;
        this.reviews = reviews;
        this.pictures = pictures;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public int getLocationID() {
        return LocationID;
    }

    public String getName() {
        return name;
    }

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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}


}
