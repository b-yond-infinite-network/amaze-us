package com.audela.challenge.busapi.security;

import com.audela.challenge.busapi.vo.UserVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BusAuthenticationToken extends AbstractAuthenticationToken {

    private final String tokenString;
    private Claims tokenClaims = new DefaultClaims();
    private List<GrantedAuthority> authorities;
    private boolean authenticated = false;
    private UserVo user = null;


    public BusAuthenticationToken(String tString) {
        super((Collection)null);
        tokenString = tString;
        authorities = new ArrayList<>(0);
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getDetails() {
        return tokenClaims.toString();
    }

    @Override
    public Object getPrincipal() {
        return user;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return tokenClaims.getSubject();
    }

    public String getToken() {
        return tokenString;
    }

    public void setTokenClaims(Claims claims) {
        this.tokenClaims = claims;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }
}