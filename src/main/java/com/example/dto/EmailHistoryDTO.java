package com.example.dto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class EmailHistoryDTO {
    private String id;
    private String email;
    private String message;
    private String status;
    private LocalDateTime createdDate;

    public EmailHistoryDTO() {
    }

    public EmailHistoryDTO(String id, String email, String message, String status, LocalDateTime createdDate) {
        this.id = id;
        this.email = email;
        this.message = message;
        this.status = status;
        this.createdDate = createdDate;
    }
}
