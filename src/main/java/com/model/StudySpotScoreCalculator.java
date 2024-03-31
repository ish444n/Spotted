import java.util.Map;

// Needs a recommendationService class

public class StudySpotScoreCalculator {
	private Map<String, Integer> maxAmenityValues;

	public StudySpotScoreCalculator(Map<String, Integer> maxAmenityValues) {
        this.maxAmenityValues = maxAmenityValues;
	}
	
	private double normalizeValueLogScale(int value, int maxValue) {
        if (value == 0) return 0; // Avoid log(0)
        double logValue = Math.log(value); 
        double logMaxValue = Math.log(maxValue);
        return logValue / logMaxValue;
    }
	
	// Assuming that there are three options to choose from: small 1 / medium 2/ large 3
	private double normalizeSeatingCapacityBasedOnPreference (int userSeatingPreference, int seatingCapacity) {
		final int SMALL_MAX = 50;
	    final int MEDIUM_MAX = 200;
	    
	    double score;

	    switch (userSeatingPreference) {
	        case 1: // User prefers small spots
	            if (seatingCapacity <= SMALL_MAX) {
	                score = 1.0; 
	            } else if (seatingCapacity <= MEDIUM_MAX) {
	                score = 0.5; 
	            } else {
	                score = 0.0; 
	            }
	            break;
	        case 2: // User prefers medium spots
	            if (seatingCapacity > SMALL_MAX && seatingCapacity <= MEDIUM_MAX) {
	                score = 1.0; 
	            } else {
	                score = 0.5; 
	            }
	            break;
	        case 3: // User prefers large spots
	            if (seatingCapacity > MEDIUM_MAX) {
	                score = 1.0; 
	            } else if (seatingCapacity > SMALL_MAX) {
	                score = 0.5; 
	            } else {
	                score = 0.0;
	            }
	            break;
	        default:
	            score = 0.0; 
	    }
	    return score;
	}
	/*
	 * 
	 * wifi
    private String openingHours;
	 */
	private double calculateComfortScore(StudySpot spot, UserPreferences userPrefs) {
		
		double comfort_score = 0;
		comfort_score += normalizeValueLogScale(spot.getSpecs().getWaterFountains(),maxAmenityValues.get("waterFountains"));
		comfort_score += normalizeValueLogScale(spot.getSpecs().getWaterFountains(),maxAmenityValues.get("restrooms"));
		comfort_score += normalizeValueLogScale(spot.getSpecs().getWaterFountains(),maxAmenityValues.get("microwaves"));
		comfort_score += normalizeValueLogScale(spot.getSpecs().getWaterFountains(),maxAmenityValues.get("refrigerators"));
		comfort_score += normalizeValueLogScale(spot.getSpecs().getWaterFountains(),maxAmenityValues.get("outlets"));
		comfort_score += normalizeValueLogScale(spot.getSpecs().getWaterFountains(),maxAmenityValues.get("AC"));
		comfort_score += normalizeSeatingCapacityBasedOnPreference(userPrefs.getPreferredStudySpaceSize(),maxAmenityValues.get("SeatingCapacity"));
		
		return comfort_score/7.0;
	}
	
	// Assuming that there are four options to choose from: very quiet 1, mild background noises 2, moderate 3, loud 4
	private double calculateNoiseLevelScore (int actual_lvl, int preferred_lvl) {
		
		double quiet_score = 0, diff = actual_lvl - preferred_lvl;
		if (diff <= 0) {
			quiet_score = 1.0;
		}
		else if (diff >= 1 && diff <= 2){
			quiet_score = 0.5;
		}
		
		return quiet_score;
	}
	
	public double calculateScore(StudySpot spot, UserPreferences userPrefs) {
		
		// default
		double connect_weight = 3.0; 
	    double is24_7_weight = 3.0; 
	    double quiet_weight = 3.0;
	    
	    double total_weight = userPrefs.getComfortAndConvenience()  + connect_weight + is24_7_weight + quiet_weight;
	    double comfort_weight = userPrefs.getComfortAndConvenience() / total_weight;
	   
		double comfort_score = calculateComfortScore(spot, userPrefs);
		
		double quiet_score = calculateNoiseLevelScore(spot.getSpecs().getNoiseLevel(), userPrefs.getNoiseLevel());
		
		double connect_score = 1.0;
		if(userPrefs.getConnectivity()) {
			if(spot.getSpecs().getWiFi() != 1) {
				connect_score = 0;
			}
		}
		
		double is24_7_score = 1.0;
		if (userPrefs.getPrefers24_7()) {
			if (!spot.getSpecs().getOpeningHours().contains("24/7")) { // don't know how to store openingHours yet, change
				is24_7_score = 0;
			}
		}
		
		double score = (comfort_score * comfort_weight) +
                (quiet_score * quiet_weight / total_weight) +
                (connect_score * connect_weight / total_weight) +
                (is24_7_score * is24_7_weight / total_weight);
		
		return score;
	}
}
