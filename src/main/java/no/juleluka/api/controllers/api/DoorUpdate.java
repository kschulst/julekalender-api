package no.juleluka.api.controllers.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import no.juleluka.api.calendar.Door;

import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static no.juleluka.api.utils.ModelMappers.nonNullMapper;

@Data
@JsonInclude(NON_NULL) // TODO: Add this as a setting in the ObjectMapper instead
public class DoorUpdate {
    private String prize;
    private String quote;
    private String instructions;

    @Size(min=7, max=256)
    private String imageUrl;

    public void populateDoor(Door door) {
        nonNullMapper().map(this, door);
    }

}
