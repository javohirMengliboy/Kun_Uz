package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TagDTO extends BaseDTO{
    private Integer id;
    private Integer orderNumber;
    private String name;

    public TagDTO() {
    }

    public TagDTO( Integer id, Integer orderNumber, String name, boolean visible, LocalDateTime createdDate) {
        super(visible, createdDate);
        this.id = id;
        this.orderNumber = orderNumber;
        this.name = name;
    }
}
