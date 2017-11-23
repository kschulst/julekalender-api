package no.juleluka.api.controllers.api;

import lombok.AllArgsConstructor;
import lombok.Value;
import no.juleluka.api.calendar.Calendar;

import static no.juleluka.api.utils.ModelMappers.looseMapper;

@Value
@AllArgsConstructor
public class CalendarPublic {
    private String id;
    private String calendarName;

    public static CalendarPublic from(Calendar calendar) {
        return looseMapper().map(calendar, CalendarPublic.class);
    }
}
