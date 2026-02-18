package com.eCommerce.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.eCommerce.entity.RoleName;
import com.eCommerce.entity.User;

import java.util.Arrays;
import java.util.Collection;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @InjectMocks
    private AuthorizationService authorizationService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testHasRole_AdminRole_ReturnsTrue() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Act
        boolean result = authorizationService.hasRole(RoleName.ROLE_ADMIN);

        // Assert
        assertTrue(result);
    }

    @Test
    void testHasRole_UserRole_ReturnsTrue() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Act
        boolean result = authorizationService.hasRole(RoleName.ROLE_USER);

        // Assert
        assertTrue(result);
    }

    @Test
    void testHasRole_NoRole_ReturnsFalse() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Act
        boolean result = authorizationService.hasRole(RoleName.ROLE_ADMIN);

        // Assert
        assertFalse(result);
    }

    @Test
    void testHasRole_NotAuthenticated_ReturnsFalse() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        // Act
        boolean result = authorizationService.hasRole(RoleName.ROLE_ADMIN);

        // Assert
        assertFalse(result);
    }

    @Test
    void testHasRole_NoAuthentication_ReturnsFalse() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act
        boolean result = authorizationService.hasRole(RoleName.ROLE_ADMIN);

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsAdmin_WithAdminRole_ReturnsTrue() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Act
        boolean result = authorizationService.isAdmin();

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsAdmin_WithoutAdminRole_ReturnsFalse() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Act
        boolean result = authorizationService.isAdmin();

        // Assert
        assertFalse(result);
    }

    @Test
    void testIsUser_WithUserRole_ReturnsTrue() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Act
        boolean result = authorizationService.isUser();

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsOwnerOrAdmin_AsAdmin_ReturnsTrue() {
        // Arrange
        User user = new User();
        user.setId(1);
        
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);

        // Act
        boolean result = authorizationService.isOwnerOrAdmin(user);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsOwnerOrAdmin_AsOwner_ReturnsTrue() {
        // Arrange
        User user = new User();
        user.setId(1);
        
        UsersDetails userDetails = mock(UsersDetails.class);
        when(userDetails.getUser()).thenReturn(user);
        
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Act
        boolean result = authorizationService.isOwnerOrAdmin(user);

        // Assert
        assertTrue(result);
    }

    @Test
    void testIsOwnerOrAdmin_AsOtherUser_ReturnsFalse() {
        // Arrange
        User user = new User();
        user.setId(1);
        
        User otherUser = new User();
        otherUser.setId(2);
        
        UsersDetails userDetails = mock(UsersDetails.class);
        when(userDetails.getUser()).thenReturn(otherUser);
        
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getAuthorities()).thenReturn((Collection) authorities);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Act
        boolean result = authorizationService.isOwnerOrAdmin(user);

        // Assert
        assertFalse(result);
    }

    @Test
    void testGetCurrentUser_WithAuthenticatedUser_ReturnsUser() {
        // Arrange
        User user = new User();
        user.setId(1);
        user.setFirstName("John");
        
        UsersDetails userDetails = mock(UsersDetails.class);
        when(userDetails.getUser()).thenReturn(user);
        
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        // Act
        User result = authorizationService.getCurrentUser();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("John", result.getFirstName());
    }

    @Test
    void testGetCurrentUser_NotAuthenticated_ReturnsNull() {
        // Arrange
        when(securityContext.getAuthentication()).thenReturn(null);

        // Act
        User result = authorizationService.getCurrentUser();

        // Assert
        assertNull(result);
    }
}
