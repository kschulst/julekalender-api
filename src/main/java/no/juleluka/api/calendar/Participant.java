package no.juleluka.api.calendar;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Participant {
    public Participant(String name) {
        this.id = ""+System.currentTimeMillis();
        this.name = name;
    }

    private String id;
    private String name;
}
