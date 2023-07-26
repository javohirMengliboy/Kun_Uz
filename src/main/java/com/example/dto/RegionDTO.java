package com.example.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class RegionDTO extends BaseOrderNumberDTO{
    public RegionDTO() {
    }

    public RegionDTO(Integer id, Integer orderNumber, String nameUz, String nameRu, String nameEng, boolean visible, LocalDateTime createdDate) {
        super(id, visible, createdDate, orderNumber, nameUz, nameRu, nameEng);
    }
}
