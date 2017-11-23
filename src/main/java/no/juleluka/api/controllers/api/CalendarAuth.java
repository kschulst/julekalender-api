package no.juleluka.api.controllers.api;

import lombok.Data;

@Data
public class CalendarAuth {
    private String calendarId;
    private String authToken;
}
