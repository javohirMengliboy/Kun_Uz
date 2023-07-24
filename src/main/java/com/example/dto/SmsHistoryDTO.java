package com.example.dto;

import com.example.enums.SmsStatus;
import com.example.enums.SmsType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class SmsHistoryDTO {
    private String id;
    private String phone;
    private String message;
    private SmsStatus status;
    private SmsType type;
    private LocalDateTime createdDate;

}
