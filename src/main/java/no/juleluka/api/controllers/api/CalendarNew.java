package no.juleluka.api.controllers.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import no.juleluka.api.calendar.Calendar;
import javax.validation.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static no.juleluka.api.utils.ModelMappers.looseMapper;

@Data
@AllArgsConstructor
public class CalendarNew {

    @NotNull @NotEmpty @Size(min=3, max=20)
    private final String calendarName;

    @NotNull @NotEmpty @Size(min=6, max=256)
    private final String adminPassword;

    public Calendar toCalendar() {
        Calendar cal = looseMapper().map(this, Calendar.class);
        return cal.init();
    }
}
