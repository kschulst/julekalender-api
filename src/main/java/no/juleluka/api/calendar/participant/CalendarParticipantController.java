package no.juleluka.api.calendar.participant;

import com.google.common.collect.ImmutableSet;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import no.juleluka.api.calendar.Calendar;
import no.juleluka.api.calendar.participant.representations.CalendarForParticipant;
import no.juleluka.api.security.AuthenticationFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping("/calendars")
@Slf4j
@Api("calendar - participants")
public class CalendarParticipantController {

    private final AuthenticationFacade auth;
    private final CalendarParticipantService calendarParticipantService;

    @Autowired
    public CalendarParticipantController(AuthenticationFacade authenticationFacade, CalendarParticipantService calendarParticipantService) {
        this.auth = requireNonNull(authenticationFacade);
        this.calendarParticipantService = requireNonNull(calendarParticipantService);
    }

    @ApiOperation("Join a calendar as a participant")
    @RequestMapping(value="/{calendarId}/participate", method = RequestMethod.POST)
    public CalendarForParticipant joinCalendar(@PathVariable Long calendarId) {
        Calendar calendar = calendarParticipantService.joinCalendar(auth.getUsername(), calendarId);
        return CalendarForParticipant.from(calendar, auth.getUsername());
    }

    @ApiOperation("Leave a calendar")
    @RequestMapping(value="/{calendarId}/participate", method = RequestMethod.DELETE)
    @PreAuthorize("isParticipant(#calendarId)")
    public CalendarForParticipant leaveCalendar(@PathVariable Long calendarId) {
        Calendar calendar = calendarParticipantService.removeParticipant(auth.getUsername(), calendarId);
        return CalendarForParticipant.from(calendar, auth.getUsername());
    }

    @ApiOperation("Retrieve participant calendars")
    @RequestMapping(method = RequestMethod.GET)
    public Set<CalendarForParticipant> getParticipantCalendars() {
        calendarParticipantService.getParticipantCalendarsForUser(auth.getUsername());
//        Calendar cal = calendarService.findCalendarById(calendarId(authToken));
//        return CalendarEdit.from(cal);
        return ImmutableSet.of();
    }

}
