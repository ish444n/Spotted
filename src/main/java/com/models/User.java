import java.util.List;

public class User {
	private int userID;
	private String username;
	private String email;
	private String password;
	private List<StudySpot> bookmarkedSpots;
	private String userBio;

	public User(int userID, String username, String email, String password, String userBio, List<StudySpot> bookmarkedSpots) {
        this.userID = userID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userBio = userBio;
        this.bookmarkedSpots = bookmarkedSpots;
    }
	
	public List<StudySpot> getBookmarkedSpots () {
		return bookmarkedSpots;
	}
	
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
    
    public String getUserBio () {
    	return userBio;
    }
    

}

