package no.juleluka.api.security;

import no.juleluka.api.user.UserInfoService;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

import static java.util.Objects.requireNonNull;

public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    private final UserInfoService userInfoService;

    private Object filterObject;
    private Object returnObject;

    public CustomMethodSecurityExpressionRoot(Authentication authentication, UserInfoService userInfoService) {
        super(authentication);
        this.userInfoService = requireNonNull(userInfoService);
    }

    public boolean isParticipant(Long calendarId) {
        String username = (String) this.getPrincipal();
        return userInfoService.get(username).isParticipantOf(calendarId);
    }

    public boolean isEditor(Long calendarId) {
        String username = (String) this.getPrincipal();
        return userInfoService.get(username).isEditorOf(calendarId);
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
