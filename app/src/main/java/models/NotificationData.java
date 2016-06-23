package models;



public class NotificationData {

	public String id;
	public String notAlert;
	public String notDate;
	public String notLocation;
	public String notification_title;
	public String image;

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getNotification_title() {
		return notification_title;
	}

	public void setNotification_title(String notification_title) {
		this.notification_title = notification_title;
	}



	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNotAlert() {
		return notAlert;
	}
	public void setNotAlert(String notAlert) {
		this.notAlert = notAlert;
	}
	public String getNotDate() {
		return notDate;
	}
	public void setNotDate(String notDate) {
		this.notDate = notDate;
	}
	public String getNotLocation() {
		return notLocation;
	}
	public void setNotLocation(String notLocation) {
		this.notLocation = notLocation;
	}
	
	

}
