public class Specification {
    private int SpecID;
    private int waterFountains;
    private int restrooms;
    private int microwaves;
    private int refrigerators;
    private int outlets;
    private int AC;
    private int WiFi;
    private int SeatingCapacity;
    private int noiseLevel; 
    private String openingHours;

    public Specification(int SpecID, int waterFountains, int restrooms, int microwaves, int refrigerators, 
                int outlets, int AC, int WiFi, int SeatingCapacity, int noiseLevel, String openingHours) {
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
    public int getSpecID() {
        return SpecID;
    }

    public int getWaterFountains() {
        return waterFountains;
    }

    public int getRestrooms() {
        return restrooms;
    }

    public int getMicrowaves() {
        return microwaves;
    }

    public int getRefrigerators() {
        return refrigerators;
    }

    public int getOutlets() {
        return outlets;
    }

    public int getAc() {
        return AC;
    }

    public int getWiFi() {
        return WiFi;
    }

    public int getSeatingCapacity() {
        return SeatingCapacity;
    }

    public int getNoiseLevel() {
        return noiseLevel;
    }

    public String getOpeningHours() {
        return openingHours;
    }
}

