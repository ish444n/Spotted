
public class User {
	private int userID;
	private String username;
	private String email;
	private String password;
	private UserLibrary userLibrary;
	private String userBio;

	public User(int userID, String username, String email, String password, String userBio) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userBio = userBio;
    }
	/*
	// servlet
	public void writeReview(Review review, StudySpot s) {
		s.addReview(review);
	}
	
	// servlet in UserLibrary
	public void updateVisitedSpots(StudySpot spot, boolean flag) {
		if (flag) {
			userLibrary.markVisited(spot);
		}
		else {
			userLibrary.unVisit(spot);
		}
	}
	
	// servlet in UserLibrary
	public void updateFavoritedSpots(StudySpot spot, boolean flag) {
		if (flag) {
			userLibrary.addFavorite(spot);
		}
		else {
			userLibrary.unFavorite(spot);
		}
	}
	
	// servlet in UserLibrary
	public void updateWantToGo(StudySpot spot, boolean flag) {
		if (flag) {
			userLibrary.wantToGo(spot);
		}
		else {
			userLibrary.dontWantToGo(spot);
		}
	}
	
	*/
	public int getUserID() {
        return userID;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserLibrary getUserLibrary() {
        return userLibrary;
    }
    
    public String getUserBio () {
    	return userBio;
    }
    

}

