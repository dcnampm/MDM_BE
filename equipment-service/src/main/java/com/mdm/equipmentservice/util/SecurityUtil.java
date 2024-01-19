package com.mdm.equipmentservice.util;

import com.mdm.equipmentservice.model.entity.User;
import com.mdm.equipmentservice.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

public class SecurityUtil {

    private static User currentLoggedInUser = null;


    public static User getCurrentLoggedInUser(UserRepository userRepository) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = user.getUsername();
//        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = principal.getClaim("preferred_username");
        if (currentLoggedInUser != null && currentLoggedInUser.getUsername().equals(username)) {
            return currentLoggedInUser;
        }
        currentLoggedInUser = userRepository.findByUsernameIgnoreCase(username).orElseThrow();
        return currentLoggedInUser;
    }
    public static Long getCurrentLoggedInUserDepartment(UserRepository userRepository) {
        User user = getCurrentLoggedInUser(userRepository);
        return user.getDepartment().getId();
    }

    public static Boolean checkCurrentLoggedInUserPermissionByDepartment(UserRepository userRepository, Long departmentId) {
        if (SecurityUtil.currentUserHasAnyAuthorities("ROLE_ADMIN", "ROLE_TPVT")) {
            return true;
        }
        return departmentId.equals(getCurrentLoggedInUserDepartment(userRepository));
    }
    public static String getCurrentLoggedInUserName(UserRepository userRepository) {
        return currentLoggedInUser.getName();
    }

    public static List<SimpleGrantedAuthority> getCurrentLoggedInUserAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().map(SimpleGrantedAuthority.class::cast).toList();
    }

    public static boolean currentUserHasAnyAuthorities(String... authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        for (String authority : authorities) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority(authority))) {
                return true;
            }
        }
        return false;
    }

    public static boolean currentUserHasAuthority(String authority) {
        return currentUserHasAnyAuthorities(authority);
    }

    public static boolean currentUserHasAllAuthorities(String... authorities) {
        for (String authority : authorities) {
            if (!currentUserHasAuthority(authority)) {
                return false;
            }
        }
        return true;
    }

    public static boolean currentUserDoesNotHaveAllAuthorities(String... authorities) {
        for (String authority : authorities) {
            if (currentUserHasAuthority(authority)) {
                return false;
            }
        }
        return true;
    }
}
