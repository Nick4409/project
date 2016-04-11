package form;

import java.util.Date;

import com.googlecode.objectify.annotation.Index;

public class GameForm {
	
    public String name;

    public String description;

    String startDateStrRepr;
    String endDateStrRepr;
    
  

    public int maxAttendees;
    public int seatsAvailable;
    
    public double latitude;
    public double longitude;
    
    public boolean cancelable;
    
    private GameForm() {}
	
    public GameForm(String name, String description, String startDate, String endDate,
    			int maxAttendees, int seatsAvailable, double latitude, double longitude, boolean cancelable) {
		this.name=name;
		this.description=description;
		this.startDateStrRepr=startDateStrRepr;
		this.endDateStrRepr=endDateStrRepr;
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
    
    public String getStartDate() {
        return startDateStrRepr;
    }
    
    public String getEndDate() {
        return endDateStrRepr;
    }
    
    private void executeDate(String date){
    //TODO	
    }
    
    
}
