package no.juleluka.api.calendar;

import no.juleluka.api.calendar.Calendar;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CalendarRepository extends MongoRepository<Calendar, Long> {

    Calendar findByCalendarName(String calendarName);
}
