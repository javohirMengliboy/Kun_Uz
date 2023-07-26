package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BaseDTO{
    private boolean visible;
    private LocalDateTime createdDate;

    public BaseDTO() {
    }

    public BaseDTO(boolean visible, LocalDateTime createdDate) {
        this.visible = visible;
        this.createdDate = createdDate;
    }


}
