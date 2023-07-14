package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class FilterResultDTO <T>{
   public List<T> list;
   public Long totalCount;

    public FilterResultDTO() {
    }

    public FilterResultDTO(List<T> list, Long totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }
}
