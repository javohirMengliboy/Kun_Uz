package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CategoryDTO {
    private Integer id;
    private Integer orderNumber;
    private String nameUz;
    private String nameRu;
    private String nameEng;
    private boolean visible;
    private LocalDateTime createdDate;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer id, Integer orderNumber, String nameUz, String nameRu, String nameEng, boolean visible, LocalDateTime createdDate) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEng = nameEng;
        this.visible = visible;
        this.createdDate = createdDate;
    }
}
