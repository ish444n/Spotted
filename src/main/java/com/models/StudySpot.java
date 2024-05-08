package com.models;

import java.util.List;

public class StudySpot {
	private int LocationID;
	private String name;
    private String description;
    private Specification specs;
    private List<Review> reviews;
    private List<Image> pictures;
    
    public StudySpot(int LocationID, String name, String description, Specification specs, List<Review> reviews, List<Image> pictures) {
        this.LocationID = LocationID;
    	this.name = name;
        this.description = description;
        this.specs = specs;
        this.reviews = reviews;
        this.pictures = pictures;
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


}
