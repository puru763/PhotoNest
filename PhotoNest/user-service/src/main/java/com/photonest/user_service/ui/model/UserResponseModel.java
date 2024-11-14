package com.photonest.user_service.ui.model;


import lombok.Data;

@Data
public class UserResponseModel {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
