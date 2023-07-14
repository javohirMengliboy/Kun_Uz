package com.example.dto;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProfileFilterDTO {
    private String name;
    private String surname;
    private String phone;
    private String password;
    private ProfileRole role;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
