package com.models;

public class Specification {
    private boolean SpecID;
    private boolean waterFountains;
    private boolean restrooms;
    private boolean microwaves;
    private boolean refrigerators;
    private boolean outlets;
    private boolean AC;
    private boolean WiFi;
    private int SeatingCapacity;
    private String noiseLevel; 
    private String openingHours;

    public Specification(boolean SpecID, boolean waterFountains, boolean restrooms, boolean microwaves, boolean refrigerators, 
    		boolean outlets, boolean AC, boolean WiFi, int SeatingCapacity, String noiseLevel, String openingHours) {
        this.SpecID = SpecID;
        this.waterFountains = waterFountains;
        this.restrooms = restrooms;
        this.microwaves = microwaves;
        this.refrigerators = refrigerators;
        this.outlets = outlets;
        this.AC = AC;
        this.WiFi = WiFi;
        this.SeatingCapacity = SeatingCapacity;
        this.noiseLevel = noiseLevel; 
        this.openingHours = openingHours;
    }

    // Getters
    public boolean getSpecID() {
        return SpecID;
    }

    public boolean getWaterFountains() {
        return waterFountains;
    }

    public boolean getRestrooms() {
        return restrooms;
    }

    public boolean getMicrowaves() {
        return microwaves;
    }

    public boolean getRefrigerators() {
        return refrigerators;
    }

    public boolean getOutlets() {
        return outlets;
    }

    public boolean getAc() {
        return AC;
    }

    public boolean getWiFi() {
        return WiFi;
    }

    public int getSeatingCapacity() {
        return SeatingCapacity;
    }

    public String getNoiseLevel() {
        return noiseLevel;
    }

    public String getOpeningHours() {
        return openingHours;
    }
}

