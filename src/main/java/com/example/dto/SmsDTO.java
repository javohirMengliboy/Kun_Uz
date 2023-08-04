package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsDTO {
    private String phone;
    private String message;

    public SmsDTO() {
    }

    public SmsDTO(String phone, String message) {
        this.phone = phone;
        this.message = message;
    }
}
