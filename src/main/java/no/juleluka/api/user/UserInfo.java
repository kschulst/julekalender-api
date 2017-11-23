package no.juleluka.api.user;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
@Data
public class UserInfo {
    @Id private String username;
    private Set<Long> editorOf = new HashSet<Long>();
    private Set<Long> participantOf = new HashSet<Long>();

    public boolean isParticipantOf(Long calendarId) {
        return participantOf.contains(calendarId);
    }

    public boolean isEditorOf(Long calendarId) {
        return editorOf.contains(calendarId);
    }

}
