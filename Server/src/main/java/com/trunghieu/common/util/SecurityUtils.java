package com.trunghieu.common.util;

import com.trunghieu.common.exception.AuthenticationException;
import com.trunghieu.common.security.UserPrincipal;
import com.trunghieu.common.util.constant.MessageConstants;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created on 9/4/2020.
 * Class: SecurityUtil.java
 * By : Admin
 */
public class SecurityUtils {
    public static UserPrincipal getUser() {
        if(!((SecurityContextHolder.getContext().getAuthentication()) instanceof AnonymousAuthenticationToken)) {
            UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken)
                    SecurityContextHolder.getContext().getAuthentication();
            if (authentication.getPrincipal() instanceof UserPrincipal) {
                return (UserPrincipal) authentication.getPrincipal();
            }
        }
        else
            throw new AuthenticationException(MessageConstants.NOT_AUTHENTICATED);
        return null;
    }

}
