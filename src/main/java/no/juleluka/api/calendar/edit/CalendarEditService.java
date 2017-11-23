package no.juleluka.api.calendar.edit;

import no.juleluka.api.calendar.Calendar;
import no.juleluka.api.calendar.CalendarException;
import no.juleluka.api.calendar.CalendarRepository;
import no.juleluka.api.user.UserInfo;
import no.juleluka.api.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class CalendarEditService {
    private final UserInfoService userInfoService;
    private final CalendarRepository calendarRepository;

    @Autowired
    public CalendarEditService(UserInfoService userDetailsService, CalendarRepository calendarRepository) {
        this.userInfoService = requireNonNull(userDetailsService);
        this.calendarRepository = requireNonNull(calendarRepository);
    }

    /**
     * Retrieves all calendars that the given user has editor privileges for.
     * Returns empty set if none.
     *
     * @param username the user's username as registered in auth0
     */
    public Set<Calendar> getEditableCalendarsForUser(String username) {
        UserInfo userInfo = userInfoService.get(username);
        return userInfo.getEditorOf().stream()
                .map(new Function<Long, Calendar>() {
                    public Calendar apply(Long calendarId) {
                        return calendarRepository.findById(calendarId).orElse(null);
                    }
                })
                .filter(cal -> cal != null)
                .collect(Collectors.toSet());
    }

    public Calendar createCalendar(Calendar calendar, String username) {
        // Don't create the calendar if a calendar with that name is already registered
        if (calendarRepository.findByCalendarName(calendar.getCalendarName()) != null) {
            // TODO: Throw standard exception?
            throw Problem.valueOf(Status.CONFLICT, "A calendar named '" + calendar.getCalendarName() + "' already exists");
        }

        calendar = calendarRepository.save(calendar);
        return addEditor(username, calendar.getId());
    }

    public Calendar addEditor(String username, Long calendarId) {
        userInfoService.addEditor(username, calendarId);
        Calendar calendar = getCalendar(calendarId);
        calendar.getEditors().add(username);
        return calendarRepository.save(calendar);
    }

    public Calendar removeEditor(String username, Long calendarId) {
        userInfoService.removeEditor(username, calendarId);

        Calendar calendar = getCalendar(calendarId);
        calendar.getEditors().remove(username);
        return calendarRepository.save(calendar);
    }

    private Calendar getCalendar(Long calendarId) {
        return calendarRepository.findById(calendarId)
                .orElseThrow(() -> new CalendarException("No calendar with id " + calendarId +  " found"));
    }

}
