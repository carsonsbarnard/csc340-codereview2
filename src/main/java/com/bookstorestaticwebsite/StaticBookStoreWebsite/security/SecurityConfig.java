package com.bookstorestaticwebsite.StaticBookStoreWebsite.security;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
        requestCache.setMatchingRequestParameterName(null);

        http
                // Disable CSRF protection (if needed for your app)
                .csrf(csrf -> csrf.disable()) // No longer using `csrf()`, now using `csrf(csrf -> csrf.disable())`

                // Authorization configuration
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                                .requestMatchers( "/admin/all", "/admin/createUserForm").hasAuthority("SUPER_ADMIN")
                                .requestMatchers("/admin/**").hasAnyAuthority("ADMIN", "SUPER_ADMIN")
                                .requestMatchers("/customer/**").hasAuthority("CUSTOMER")
                                .requestMatchers("/register").permitAll() // Allow everyone to access /register
                                .anyRequest().authenticated()
                )

                .formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(new CustomAuthenticationSuccessHandler())
                        .permitAll()
                )
                .sessionManagement((session) -> session
                        .maximumSessions(1) // Limits to one session per user
                        .expiredUrl("/login?expired=true") // Redirect if session expires
                )

                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                )

                // Exception handling
                .exceptionHandling(ex -> ex.accessDeniedPage("/403"))

                // Request cache configuration
                .requestCache(cache -> cache.requestCache(requestCache));

        return http.build();
    }
}
