package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CategoryDTO extends BaseOrderNumberDTO{
    public CategoryDTO() {
    }

    public CategoryDTO(Integer id, Integer orderNumber, String nameUz, String nameRu, String nameEng, boolean visible, LocalDateTime createdDate) {
        super(id, visible, createdDate, orderNumber, nameUz, nameRu, nameEng);
    }
}
