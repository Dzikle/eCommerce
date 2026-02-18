package com.eCommerce.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eCommerce.entity.RoleName;
import com.eCommerce.entity.User;

/**
 * Service providing authorization utility methods
 */
@Service
public class AuthorizationService {

    /**
     * Check if the current user has a specific role
     * @param roleName the role to check
     * @return true if user has the role, false otherwise
     */
    public boolean hasRole(RoleName roleName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> authority.equals("ROLE_" + roleName.name().replace("ROLE_", "")));
    }

    /**
     * Check if the current user is an admin
     * @return true if user is admin, false otherwise
     */
    public boolean isAdmin() {
        return hasRole(RoleName.ROLE_ADMIN);
    }

    /**
     * Check if the current user is a regular user
     * @return true if user is a regular user, false otherwise
     */
    public boolean isUser() {
        return hasRole(RoleName.ROLE_USER);
    }

    /**
     * Check if the current authenticated user is the owner of the given user entity
     * @param user the user entity to check ownership
     * @return true if current user is the owner or admin, false otherwise
     */
    public boolean isOwnerOrAdmin(User user) {
        if (isAdmin()) {
            return true;
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof UsersDetails) {
            UsersDetails userDetails = (UsersDetails) principal;
            return userDetails.getUser().getId().equals(user.getId());
        }
        
        return false;
    }

    /**
     * Get the currently authenticated user
     * @return the current user or null if not authenticated
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof UsersDetails) {
            UsersDetails userDetails = (UsersDetails) principal;
            return userDetails.getUser();
        }
        
        return null;
    }
}
