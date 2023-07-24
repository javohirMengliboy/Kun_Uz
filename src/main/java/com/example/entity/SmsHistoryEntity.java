package com.example.entity;

import com.example.enums.SmsStatus;
import com.example.enums.SmsType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@Table(name = "sms_history")
public class SmsHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "message")
    private String message;

    @Column(name = "status")
    private SmsStatus status;

    @Column(name = "type")
    private SmsType type;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
}
