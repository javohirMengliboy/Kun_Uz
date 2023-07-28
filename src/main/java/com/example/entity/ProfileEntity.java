package com.example.entity;

import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseIdentityEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProfileRole role;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "image_id")
    private AttachEntity image;

    @Column(name = "prt_id")
    private Integer prtId;
}
