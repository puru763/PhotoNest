package com.photonest.user_service.ui.model;


import lombok.Data;

@Data
public class LoginRequestModel {

    private String email;
    private String password;
}
