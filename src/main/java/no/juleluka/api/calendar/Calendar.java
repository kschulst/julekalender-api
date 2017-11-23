package no.juleluka.api.calendar;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "calendars")
@Data
public class Calendar {

    @Id private Long id;
    private String calendarName;
    private String logoUrl;
    private Integer winnersPerDay;

    // Whether or not date validation will be performed on door opening
    private Boolean doorsAlwaysAvailable;

    private List<Integer> doorSequence;
    private List<Door> doors = new ArrayList();
//    private Set<Participant> participants = new HashSet<>();
    private Set<String> participants = new HashSet<>();
    private Set<String> editors = new HashSet<>();
    private boolean joinable = true;

    public Calendar init() {
        id = System.currentTimeMillis(); // Good enough
        for (int i=1; i<=24; i++) {
            doors.add(new Door(i));
        }
        this.doorSequence = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24);
        Collections.shuffle(this.doorSequence);
        return this;
    }

    public Calendar withCalendarName(String calendarName) {
        this.calendarName = calendarName;
        return this;
    }

    public Door getDoor(int doorNumber) {
        return doors.get(doorNumber-1);
    }

    public void setDoor(int doorNumber, Door door) {
        doors.set(doorNumber-1, door);
    }
}
