package com.example.mapper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegionLanguageMapper {
    private Integer id;
    private Integer orderNumber;
    private String name;

    public RegionLanguageMapper() {
    }

    public RegionLanguageMapper(Integer id, Integer orderNumber, String name) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.name = name;
    }
}
