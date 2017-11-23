package no.juleluka.api.calendar.edit.representations;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import no.juleluka.api.calendar.Calendar;
import no.juleluka.api.controllers.api.DoorAdmin;
import no.juleluka.api.controllers.api.ParticipantAdmin;

import java.util.List;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static no.juleluka.api.utils.ModelMappers.looseMapper;

@Data
@JsonInclude(NON_NULL)
public class CalendarEdit {
    private Long id;
    private String calendarName;
    private String logoUrl;
    private String contactEmail;
    private Boolean doorsAlwaysAvailable;
    private Boolean joinable;
    private Integer winnersPerDay;
    private List<Integer> doorSequence;
    private List<DoorAdmin> doors;
    private Set<String> participants;
    private Set<String> editors;

    public static CalendarEdit from(Calendar calendar) {
        return looseMapper().map(calendar, CalendarEdit.class);
    }

}
