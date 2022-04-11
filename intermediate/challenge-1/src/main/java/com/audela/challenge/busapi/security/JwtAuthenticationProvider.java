package com.audela.challenge.busapi.security;

import com.audela.challenge.busapi.exception.BusAuthenticationException;
import com.audela.challenge.busapi.vo.UserVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.xml.bind.DatatypeConverter;

public class JwtAuthenticationProvider implements AuthenticationProvider {

    private String SECRET_KEY = "abcd";
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BusAuthenticationToken jwtAuth = (BusAuthenticationToken)authentication;
        try {
            String authToken = jwtAuth.getToken();
            if(authToken != null && authToken.startsWith("Bearer ")){
                authToken = authToken.substring(7);
                Claims claims = Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                        .parseClaimsJws(authToken).getBody();
                jwtAuth.setTokenClaims(claims);
                UserVo user = new UserVo();
                user.setFirstName(claims.get("firstName",String.class));
                user.setLastName(claims.get("lastName",String.class));
                jwtAuth.setUser(user);
                jwtAuth.setAuthenticated(true);
            }else{
                throw new BusAuthenticationException("Authentication token missing");
            }
        }catch (Exception e){
            throw new BusAuthenticationException("Authentication failed",e);
        }
        return jwtAuth;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BusAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
