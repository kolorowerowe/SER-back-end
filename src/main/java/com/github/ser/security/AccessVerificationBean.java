package com.github.ser.security;


import com.github.ser.model.database.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class AccessVerificationBean {

    public boolean hasAccessToCompany(String companyId) {
        if (companyId == null) {
            log.debug("Company id is null, access denied");
            return false;
        }

        if (isAdminOrOrganizer()) {
            log.debug("User is admin or organiser. Access granted.");
            return true;
        }

        if (isCompanyInUserCompanyAccessList(companyId)) {
            log.debug("User has company in access list. Access granted.");
            return true;
        }

        log.debug("Access denied.");
        return false;


    }

    public boolean isAdminOrOrganizer() {
        User user = getUserFromContext();
        switch (user.getRole()) {
            case SYSTEM_ADMIN:
            case ORGANIZER_EDITOR:
            case ORGANIZER_VIEWER:
                return true;
            case COMPANY_EDITOR:
            case ACTIVATE_ACCOUNT:
            default:
                return false;
        }

    }

    public boolean isCompanyInUserCompanyAccessList(String companyId) {
        User user = getUserFromContext();
        return user.getCompanyAccessList().stream().map(companyAccess -> companyAccess.getCompanyId().toString()).anyMatch(s -> s.equals(companyId));
    }

    public User getUserFromContext() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}