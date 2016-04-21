package form;

import java.util.Date;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GameForm {
	
    public String name;

    public String description;

    public long startDate;
    public String startDateStr;
    
    public long endDate;
    public String endDateStr;

    public int maxAttendees;
    public int seatsAvailable;
    //найменування спорту
    public String sport;
    
    public String marker;
    
    public boolean cancelable;
    
    
    private GameForm() {}
	
    public GameForm(String name, String description, String startDateStr, String endDateStr, String sport,   
    			int maxAttendees, int seatsAvailable, String marker, boolean cancelable) {
		this.name=name;
		this.description=description;
		this.sport=sport;
		this.startDateStr=startDateStr;
		this.endDateStr=endDateStr;
		this.maxAttendees=maxAttendees;
		this.seatsAvailable=seatsAvailable;
		this.marker=marker;
		this.cancelable=cancelable;
		this.startDate=createDate(startDateStr);
		this.endDate=createDate(endDateStr);
	}
    
    
    
    public long createDate(String dateStr){
    	int month=Integer.parseInt(dateStr.substring(0, 2));
    	int day=Integer.parseInt(dateStr.substring(3, 5));
    	int year=Integer.parseInt(dateStr.substring(6, 10));
    	int hours;
    	int minutes;
    	if(dateStr.length()==18){
    		hours = Integer.parseInt(dateStr.substring(11, 12));
    		minutes = Integer.parseInt(dateStr.substring(13, 15));
    	}
    	else{
    		hours = Integer.parseInt(dateStr.substring(11, 13));
        	minutes = Integer.parseInt(dateStr.substring(14, 16));
    	}
    	if(dateStr.endsWith("PM")){
    		hours+=12;
    	}
    	Date date = new Date(year, month, day, hours, minutes);
    	long res =date.getTime();
    	return res;
    }
    
    public String getName() {
        return name;
    }
    public String getMarker() {
        return marker;
    }
    public String getDescription() {
        return description;
    }

    public String getStartDateStr() {
        return startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }
    
    public int getMaxAttendees() {
        return maxAttendees;
    }
    
    public int getSeatsAvailable() {
        return seatsAvailable;
    }
    
  
    
    public boolean getCancelable() {
        return cancelable;
    }

}
