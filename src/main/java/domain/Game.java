package domain;

import java.util.Calendar;
import java.util.Date;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.google.appengine.api.search.checkers.Preconditions;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import form.GameForm;
import static service.OfyService.ofy;
@Entity
public class Game {
	@Id
    public long id;
    
	@Index
    public String name;
    
    public String description;
    
    @Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Key<Profile> profileKey;
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private String organizerUserId;
    @Index
    private long startDate;
    public String startDateStr;
    @Index
    private long endDate;
    public String endDateStr;
    
    public double latitude;
    public double longitude;
    
    public String sport;

    @Index
    public int maxAttendees;

    @Index
    public int seatsAvailable;
    
    
    private boolean cancelable;
    //необхідність
    private Game() {}
    
    public Game(final long id, final String organizerUserId, final GameForm gameForm) {
    	Preconditions.checkNotNull(gameForm.getName(), "The name is required");
        this.id = id;
        this.profileKey = Key.create(Profile.class, organizerUserId);
        this.organizerUserId = organizerUserId;
        updateWithGameForm(gameForm);
	}
    
    public void updateWithGameForm(GameForm gameForm){
    	this.name = gameForm.getName();
        this.description = gameForm.getDescription();
        this.sport=gameForm.sport;
        startDateStr = gameForm.getStartDateStr();
        endDateStr = gameForm.getEndDateStr();
        if(gameForm.endDate==0||gameForm.startDate==0){
        	endDate=gameForm.createDate(endDateStr);
        	startDate=gameForm.createDate(startDateStr);
        }
        else{
        	startDate=gameForm.startDate;
        	endDate=gameForm.endDate;
        }
        int seatsAllocated = maxAttendees - seatsAvailable;
        if (gameForm.getMaxAttendees() < seatsAllocated) {
            throw new IllegalArgumentException(seatsAllocated + " seats are already allocated, "
                    + "but you tried to set maxAttendees to " + gameForm.getMaxAttendees());
        }
        
        this.maxAttendees = gameForm.getMaxAttendees();
        this.seatsAvailable = this.maxAttendees - seatsAllocated;
        this.latitude=gameForm.getLatitude();
        this.longitude=gameForm.getLongtitude();
        this.cancelable=gameForm.getCancelable();
    }
    
    public void bookSeats(final int number) {
        if (seatsAvailable < number) {
            throw new IllegalArgumentException("There are no seats available.");
        }
        seatsAvailable = seatsAvailable - number;
    }

    public void giveBackSeats(final int number) {
        if (seatsAvailable + number > maxAttendees) {
            throw new IllegalArgumentException("The number of seats will exceeds the capacity.");
        }
        seatsAvailable = seatsAvailable + number;
    }
    
    public String getOrganizerDisplayName() {
        Profile organizer = ofy().load().key(Key.create(Profile.class, organizerUserId)).now();
        //Profile organizer = ofy().load().key(getProfileKey()).now();
        if (organizer == null) {
            return organizerUserId;
        } else {
            return organizer.getName();
        }
    }
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public String getOrganizerUserId() {
        return organizerUserId;
    }
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    public Key<Profile> getProfileKey() {
        return profileKey;
    }
   


    public int getMaxAttendees() {
        return maxAttendees;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }
    
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
 
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Id: " + id + "\n")
                .append("Name: ").append(name).append("\n");
        
        if (description != null) {            
            stringBuilder.append("Description").append(description).append("\n");
        }
//        if (date != null) {
//            stringBuilder.append("Start date: ").append(date.toString()).append("\n");
//        }
        stringBuilder.append("Max attendees: ").append(maxAttendees).append("\n");
        return stringBuilder.toString();
    }
}
