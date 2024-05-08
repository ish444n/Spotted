package com.models;

public class Specification {
    private int SpecID;
    private boolean waterFountains;
    private boolean restrooms;
    private boolean microwaves;
    private boolean refrigerators;
    private boolean outlets;
    private boolean AC;
    private boolean WiFi;
    private int SeatingCapacity;
    private String noiseLevel; 

    public Specification(int SpecID, boolean waterFountains, boolean restrooms, boolean microwaves, boolean refrigerators, 
    		boolean outlets, boolean AC, boolean WiFi, int SeatingCapacity, String noiseLevel) {
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
    }

 // Corrected boolean getters
    public int getSpecID() {
        return SpecID;
    }

    public boolean hasWaterFountains() {
        return waterFountains;
    }

    public boolean hasRestrooms() {
        return restrooms;
    }

    public boolean hasMicrowaves() {
        return microwaves;
    }

    public boolean hasRefrigerators() {
        return refrigerators;
    }

    public boolean hasOutlets() {
        return outlets;
    }

    public boolean hasAC() {
        return AC;
    }

    public boolean hasWiFi() {
        return WiFi;
    }

    public int getSeatingCapacity() {
        return SeatingCapacity;
    }

    public String getNoiseLevel() {
        return noiseLevel;
    }

}