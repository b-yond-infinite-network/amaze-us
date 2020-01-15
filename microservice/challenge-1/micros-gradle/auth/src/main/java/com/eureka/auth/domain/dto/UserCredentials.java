package com.eureka.auth.domain.dto;

/**
 *  UserCredentials final class.
 *
 * @author Miguel Gonzalez (maggonzz@gmail.com)
 * @since 0.0.1
 */
// A (temporary) class just to represent the user credentials
public final class UserCredentials {
    private String username, password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
