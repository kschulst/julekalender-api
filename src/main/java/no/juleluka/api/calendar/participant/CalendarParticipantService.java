package no.juleluka.api.calendar.participant;

import no.juleluka.api.calendar.Calendar;
import no.juleluka.api.calendar.CalendarException;
import no.juleluka.api.calendar.CalendarRepository;
import no.juleluka.api.user.UserInfo;
import no.juleluka.api.user.UserInfoRepository;
import no.juleluka.api.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@Service
public class CalendarParticipantService {
    private final UserInfoRepository userDetailsRepository;
    private final UserInfoService userInfoService;
    private final CalendarRepository calendarRepository;

    @Autowired
    public CalendarParticipantService(UserInfoRepository userDetailsRepository, UserInfoService userInfoService, CalendarRepository calendarRepository) {
        this.userDetailsRepository = requireNonNull(userDetailsRepository);
        this.userInfoService = requireNonNull(userInfoService);
        this.calendarRepository = requireNonNull(calendarRepository);
    }

    /**
     * Retrieves all calendars that the given user has participant privileges for.
     * Returns empty set if none.
     *
     * @param username the user's username as registered in auth0
     */
    public Set<Calendar> getParticipantCalendarsForUser(String username) {
        UserInfo user = userInfoService.get(username);
        return user.getParticipantOf().stream()
                .map(new Function<Long, Calendar>() {
                    public Calendar apply(Long calendarId) {
                        return calendarRepository.findById(calendarId).orElse(null);
                    }
                })
                .collect(Collectors.toSet());
    }

    public Calendar joinCalendar(String username, Long calendarId) {
        Calendar calendar = getCalendar(calendarId);
        if (calendar.isJoinable()) {
            return addParticipant(username, calendarId);
        }
        else {
            throw new CalendarException("Calendar " + calendarId + " does not allow joining");
        }
    }

    public Calendar addParticipant(String username, Long calendarId) {
        userInfoService.addParticipant(username, calendarId);

        Calendar calendar = getCalendar(calendarId);
        calendar.getParticipants().add(username);
        return calendarRepository.save(calendar);
    }

    public Calendar removeParticipant(String username, Long calendarId) {
        userInfoService.removeParticipant(username, calendarId);

        Calendar calendar = getCalendar(calendarId);
        calendar.getParticipants().remove(username);
        return calendarRepository.save(calendar);
    }

    private Calendar getCalendar(Long calendarId) {
        return calendarRepository.findById(calendarId)
                .orElseThrow(() -> new CalendarException("No calendar with id " + calendarId +  " found"));
    }

}
