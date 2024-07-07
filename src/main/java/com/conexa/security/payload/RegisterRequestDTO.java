package com.conexa.security.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequestDTO {

    private String email;
    private String password;

    public RegisterRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
