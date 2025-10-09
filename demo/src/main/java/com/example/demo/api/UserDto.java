// UserDto.java
package com.example.demo.api;

import com.example.demo.user.User;

public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String device;

    public UserDto(Long id, String name, String email, String device) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.device = device;
    }

    public static UserDto fromEntity(User user) {
        return new UserDto(user.getId(), user.getSurname(), user.getEmail(), null);
    }
}
