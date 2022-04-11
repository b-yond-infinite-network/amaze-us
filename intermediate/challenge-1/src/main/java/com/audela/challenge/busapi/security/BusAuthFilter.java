package com.audela.challenge.busapi.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class BusAuthFilter extends AuthenticationFilter {
    public BusAuthFilter(AuthenticationManager authenticationManager, AuthenticationConverter authenticationConverter) {
        super(authenticationManager, authenticationConverter);
        setSuccessHandler(new JwtAuthenticationSuccessHandler());
    }
}
