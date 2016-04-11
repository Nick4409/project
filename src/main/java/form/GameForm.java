package form;

import java.util.Date;

public class GameForm {
	
    public String name;

    public String description;

    public Date date;
  

    public int maxAttendees;
    public int seatsAvailable;
    
    public double latitude;
    public double longitude;
    
    public boolean cancelable;
    
    private GameForm() {}
	
    public GameForm(String name, String description, Date date, 
    			int maxAttendees, int seatsAvailable, double latitude, double longitude, boolean cancelable) {
		this.name=name;
		this.description=description;
		this.date=date;
		this.maxAttendees=maxAttendees;
		this.seatsAvailable=seatsAvailable;
		this.latitude=latitude;
		this.longitude=longitude;
		this.cancelable=cancelable;
	}
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getStartDate() {
        return date;
    }

    public int getMaxAttendees() {
        return maxAttendees;
    }
    
    public int getSeatsAvailable() {
        return seatsAvailable;
    }
    
    public double getLatitude() {
        return latitude;
    }
    
    public double getLongtitude() {
        return longitude;
    }
    
    public boolean getCancelable() {
        return cancelable;
    }

}
