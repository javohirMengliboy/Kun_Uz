package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RegistrationDTO {
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phone;

    public RegistrationDTO() {
    }

    public RegistrationDTO(String name, String surname, String email, String password, String phone) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
}