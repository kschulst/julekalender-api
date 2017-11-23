package no.juleluka.api.controllers.api;

import lombok.Data;
import no.juleluka.api.calendar.Participant;

import static no.juleluka.api.utils.ModelMappers.looseMapper;

@Data
public class ParticipantAdmin {
    private String id;
    private String name;

    public static ParticipantAdmin from(Participant participant) {
        return looseMapper().map(participant, ParticipantAdmin.class);
    }

}
