package com.bookstorestaticwebsite.StaticBookStoreWebsite.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("SUPER_ADMIN") || roles.contains("ADMIN")) {
            response.sendRedirect("/admin/");
        } else if (roles.contains("CUSTOMER")) {
            response.sendRedirect("/customer/index");
        } else {
            response.sendRedirect("/403"); // Access denied for unhandled roles
        }
    }
}
