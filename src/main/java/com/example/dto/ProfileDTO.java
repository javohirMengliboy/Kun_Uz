package com.example.dto;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String password;
    private ProfileStatus status;
    private ProfileRole role;
    private Integer visible;
    private LocalDateTime createdDate;
    private Integer photoId;
    private String jwt;

    public ProfileDTO() {
    }

    public ProfileDTO(Integer id, String name, String surname, String email, String phone, String password, ProfileStatus status, ProfileRole role, boolean visible, LocalDateTime createdDate, Integer photoId) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.status = status;
        this.role = role;
        this.visible = visible ? 1 : 0;
        this.createdDate = createdDate;
        this.photoId = photoId;
    }
}
