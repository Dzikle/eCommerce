package com.eCommerce;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Authorization Configuration
 * Enables method-level security using @PreAuthorize, @PostAuthorize, @Secured annotations
 */
@Configuration
@EnableGlobalMethodSecurity(
    prePostEnabled = true,  // Enables @PreAuthorize and @PostAuthorize
    securedEnabled = true,  // Enables @Secured
    jsr250Enabled = true    // Enables @RolesAllowed
)
public class AuthorizationConfiguration extends GlobalMethodSecurityConfiguration {
    // Method-level security is now enabled across the application
}
