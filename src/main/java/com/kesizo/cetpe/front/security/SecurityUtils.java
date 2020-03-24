package com.kesizo.cetpe.front.security;

import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;

public final class SecurityUtils {

    private SecurityUtils() {
        // Util methods only so static class by making this class non-instantiable
    }

    /**
     * It determines if a request is internal to Vaadin
     *
     * @param request
     * @return
     */
    static boolean isFrameworkInternalRequest(HttpServletRequest request) {

        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);

        return parameterValue != null
            && Stream.of(ServletHelper.RequestType.values())
            .anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

    /**
     * It checks if the current user is logged in
     *
     * @return
     */
    static boolean isUserLoggedIn() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication != null
            && !(authentication instanceof AnonymousAuthenticationToken)
            && authentication.isAuthenticated();
    }
}