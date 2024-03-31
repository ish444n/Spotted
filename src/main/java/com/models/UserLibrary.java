
import java.util.List;

public class UserLibrary {
    private List<StudySpot> visitedSpots;
    private List<StudySpot> favoritedSpots;
    private List<StudySpot> wantToGoSpots;

    public UserLibrary(List<StudySpot> visitedSpots, List<StudySpot> favoritedSpots,  List<StudySpot> wantToGoSpots) {
        this.visitedSpots = visitedSpots;
        this.favoritedSpots = favoritedSpots;
        this.wantToGoSpots = wantToGoSpots;
    }
    
    /*
    // servlet
    public void addFavorite(StudySpot spot) {
        if (!favoritedSpots.contains(spot)) {
            favoritedSpots.add(spot);
        }
    }
    
    //servlet
    public void unFavorite(StudySpot spot) {
        favoritedSpots.remove(spot);
    }

    //servlet
    public void markVisited(StudySpot spot) {
        if (!visitedSpots.contains(spot)) {
            visitedSpots.add(spot);
            if (wantToGoSpots.contains(spot)) {
            	wantToGoSpots.remove(spot);
            }
        }
    }

    //servlet
    public void unVisit(StudySpot spot) {
        visitedSpots.remove(spot);
    }
    
    //servlet
    public void wantToGo(StudySpot spot) {
    	// otherwise notify user that they've been to the place
        if (!wantToGoSpots.contains(spot) && !visitedSpots.contains(spot)) {
            wantToGoSpots.add(spot);
        }
    }

    //servlet
    public void dontWantToGo(StudySpot spot) {
        wantToGoSpots.remove(spot);
    }
    */
    // Getters 
    public List<StudySpot> getVisitedSpots() {
        return visitedSpots;
    }

    public List<StudySpot> getFavoritedSpots() {
        return favoritedSpots;
    }

    public List<StudySpot> getWantToGoSpots() {
        return wantToGoSpots;
    }
}
