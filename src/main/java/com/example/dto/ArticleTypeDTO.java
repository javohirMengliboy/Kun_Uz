package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ArticleTypeDTO extends BaseOrderNumberDTO{
    public ArticleTypeDTO() {
    }

    public ArticleTypeDTO(Integer id, boolean visible, LocalDateTime createdDate, Integer orderNumber, String nameUz, String nameRu, String nameEng) {
        super(id, visible, createdDate, orderNumber, nameUz, nameRu, nameEng);
    }
}
