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
    private long id;
    
	@Index
    String name;
    	
    String description;
    
    @Index
    String sport;
    
    
    @Parent
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private Key<Profile> profileKey;
    
    @ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
    private String organizerUserId;
    
    @Index
    int startDate;
    @Index
    int endDate;
    
    String startDateStrRepr;
    String endDateStrRepr;

    private double latitude;
    private double longitude;
    
    

    @Index
    private int maxAttendees;

    @Index
    private int seatsAvailable;
    
    boolean cancelable;
    
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
        this.startDateStrRepr=gameForm.getStartDate();
        this.endDateStrRepr=gameForm.getEndDate();
        
        //TODO робота з часом
        
        // Check maxAttendees value against the number of already allocated seats.
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
        if (sport != null) {
            stringBuilder.append("Sport: ").append(sport).append("\n");
        }
        if (description != null) {            
            stringBuilder.append("Description").append(description).append("\n");
        }
        if (startDateStrRepr != null) {
            stringBuilder.append("StartDate: ").append(startDateStrRepr).append("\n");
        }
        if (endDateStrRepr != null) {
            stringBuilder.append("EndDate: ").append(endDateStrRepr).append("\n");
        }
        stringBuilder.append("Max Attendees: ").append(maxAttendees).append("\n");
        return stringBuilder.toString();
    }
}
