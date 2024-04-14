
public class Image {
	private String filePath;
	private int imageID;
	private int locationID;
	
	public Image (String filePath, int imageID, int locationID) {
		this.filePath = filePath;
		this.imageID = imageID;
		this.locationID = locationID;
	}
	
	public String getImageFilePath () {
		return filePath;
	}
	
	public int getImageID () {
		return imageID;
	}
	
	public int getLocationId () {
		return locationID;
	}
}
