package com.models;

public class UserPreferences {
    private int userId;
    private int comfortAndConvenience; // scale of 0-5
    private int preferredStudySpaceSize; // Small 1/Medium 2/Large 3
    private int noiseLevel; // very quiet 1, mild background noises 2, moderate 3, loud 4
    private boolean connectivity;
    private boolean prefers24_7;
    
    public UserPreferences(int userId, int comfortAndConvenience, int preferredStudySpaceSize, int noiseLevel, boolean prefers24_7, boolean connectivity) {
        this.userId = userId;
        this.comfortAndConvenience = comfortAndConvenience;
        this.connectivity = connectivity;
        this.preferredStudySpaceSize = preferredStudySpaceSize;
        this.noiseLevel = noiseLevel;
        this.prefers24_7 = prefers24_7;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public int getComfortAndConvenience() {
        return comfortAndConvenience;
    }

    public int getPreferredStudySpaceSize() {
        return preferredStudySpaceSize;
    }

    public int getNoiseLevel() {
        return noiseLevel;
    }
    
    public boolean getConnectivity() {
        return connectivity;
    }
    
    public boolean getPrefers24_7() {
        return prefers24_7;
    }

}
