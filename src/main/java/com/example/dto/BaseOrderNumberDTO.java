package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class BaseOrderNumberDTO extends BaseDTO{
    private Integer id;
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEng;
    private boolean visible;
    private LocalDateTime createdDate;

    public BaseOrderNumberDTO() {
    }

    public BaseOrderNumberDTO(Integer id, boolean visible, LocalDateTime createdDate, Integer orderNumber, String nameUz, String nameRu, String nameEng) {
        super(visible, createdDate);
        this.id = id;
        this.orderNumber = orderNumber;
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEng = nameEng;
    }
}
