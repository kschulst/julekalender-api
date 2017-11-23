package no.juleluka.api.controllers.api;

import lombok.Data;
import no.juleluka.api.calendar.Participant;

import static no.juleluka.api.utils.ModelMappers.looseMapper;

@Data
public class ParticipantPublic {
    private String id;
    private String name;

    public static ParticipantPublic from(Participant participant) {
        return looseMapper().map(participant, ParticipantPublic.class);
    }

}
