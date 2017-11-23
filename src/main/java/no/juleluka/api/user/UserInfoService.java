package no.juleluka.api.user;

import no.juleluka.api.calendar.CalendarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.Objects.requireNonNull;

@Service
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoService(UserInfoRepository userInfoRepository, CalendarRepository calendarRepository) {
        this.userInfoRepository = requireNonNull(userInfoRepository);
    }

    public UserInfo get(String username) {
        UserInfo userInfo = userInfoRepository.findById(username).orElse(null);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setUsername(username);
            userInfo =  userInfoRepository.save(userInfo);
        }

        return userInfo;
    }

    public UserInfo addParticipant(String username, Long calendarId) {
        UserInfo userInfo = get(username);
        userInfo.getParticipantOf().add(calendarId);
        return userInfoRepository.save(userInfo);
    }

    public UserInfo removeParticipant(String username, Long calendarId) {
        UserInfo userInfo = get(username);
        userInfo.getParticipantOf().remove(calendarId);
        return userInfoRepository.save(userInfo);
    }

    public UserInfo addEditor(String username, Long calendarId) {
        UserInfo userInfo = get(username);
        userInfo.getEditorOf().add(calendarId);
        return userInfoRepository.save(userInfo);
    }

    public UserInfo removeEditor(String username, Long calendarId) {
        UserInfo userInfo = get(username);
        userInfo.getEditorOf().remove(calendarId);
        return userInfoRepository.save(userInfo);
    }

}
