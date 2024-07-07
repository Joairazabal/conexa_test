package com.conexa.security.payload;

import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String password;
    
    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}