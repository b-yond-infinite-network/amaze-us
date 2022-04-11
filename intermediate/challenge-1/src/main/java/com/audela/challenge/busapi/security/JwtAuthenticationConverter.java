package com.audela.challenge.busapi.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationConverter;

import javax.servlet.http.HttpServletRequest;

public class JwtAuthenticationConverter implements AuthenticationConverter {
    @Override
    public Authentication convert(HttpServletRequest request) {
        BusAuthenticationToken authRequest = new BusAuthenticationToken(request.getHeader("Authorization"));
        return authRequest;
    }
}
