// RegisterData.java
package com.example.demo.api;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterData {
    @JsonProperty("API_TIME")
    private long apiTime;
    private UserDto user;
    @JsonProperty("user_logged_in")
    private boolean userLoggedIn;

    public RegisterData(long apiTime, UserDto user, boolean userLoggedIn) {
        this.apiTime = apiTime;
        this.user = user;
        this.userLoggedIn = userLoggedIn;
    }
    // getters/setters
}
