package form;

import java.util.Date;

public class GameForm {
	
    private String name;

    private String description;

    private Date startDate;
    private Date endDate;

    private int maxAttendees;
    private int seatsAvailable;
    
    private double latitude;
    private double longitude;
    
    private boolean cancelable;
    
    private GameForm() {}
	
    public GameForm(String name, String description, Date startDate, Date endDate,
    			int maxAttendees, int seatsAvailable, double latitude, double longitude, boolean cancelable) {
		this.name=name;
		this.description=description;
		this.startDate=startDate;
		this.endDate=endDate;
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
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
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
