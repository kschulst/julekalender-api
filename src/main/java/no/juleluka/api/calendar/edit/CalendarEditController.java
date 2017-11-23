package no.juleluka.api.calendar.edit;

import com.google.common.collect.ImmutableList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import no.juleluka.api.calendar.Calendar;
import no.juleluka.api.calendar.CalendarRepository;
import no.juleluka.api.calendar.edit.representations.CalendarEdit;
import no.juleluka.api.controllers.api.CalendarNew;
import no.juleluka.api.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/edit/calendars")
@Slf4j
@Api("calendar - edit")
public class CalendarEditController {

    private final AuthenticationFacade auth;
    private final CalendarRepository calendarRepository;
    private final CalendarEditService calendarEditService;

    @Autowired
    public CalendarEditController(AuthenticationFacade authenticationFacade, CalendarEditService calendarEditService, CalendarRepository calendarRepository) {
        this.auth = requireNonNull(authenticationFacade);
        this.calendarEditService = requireNonNull(calendarEditService);
        this.calendarRepository = requireNonNull(calendarRepository);
    }

    @ApiOperation("Create a new calendar")
    @RequestMapping(method = RequestMethod.POST)
    public CalendarEdit createCalendar(@Valid @RequestBody CalendarNew newCalendar) {
        Calendar calendar = calendarEditService.createCalendar(newCalendar.toCalendar(), auth.getUsername());
        return CalendarEdit.from(calendar);
    }

    @ApiOperation("Retrieve editable calendars")
    @RequestMapping(method = RequestMethod.GET)
    public Set<CalendarEdit> getEditableCalendars() {
        Set<Calendar> calendars = calendarEditService.getEditableCalendarsForUser(auth.getUsername());
        return calendars.stream().map(cal -> CalendarEdit.from(cal)).collect(Collectors.toSet());
    }

//    @ApiOperation("Retrieve editor view of calendar")
//    @RequestMapping(value = "{id}", method = RequestMethod.POST)
//    public CalendarEdit getCalendarById(@PathVariable(value="id") String id) {
//        Calendar cal = calendarService.findCalendarById(calendarId(authToken));
//        return CalendarEdit.from(cal);
//    }
}
