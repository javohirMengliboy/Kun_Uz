package com.example.util;

import com.example.dto.BaseOrderNumberDTO;
import com.example.entity.BaseOrderNumberEntity;
import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import com.example.mapper.LanguageI;
import com.example.mapper.LanguageMapper;

import java.util.ArrayList;
import java.util.List;

public class ServiceUtil{

    public static BaseOrderNumberEntity save(BaseOrderNumberDTO dto){
        BaseOrderNumberEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        return entity;
    }

    public static List<LanguageMapper> getByLanguage(List<LanguageI> list){
        List<LanguageMapper> mapperList = new ArrayList<>();
        list.forEach(temp -> {
            LanguageMapper mapper = new LanguageMapper();
            mapper.setId(temp.getId());
            mapper.setOrderNumber(temp.getOrderNumber());
            mapper.setName(temp.getName());
            mapperList.add(mapper);
        });
        return mapperList;
    }

    public static Object checkingForUpdate(BaseOrderNumberDTO dto, BaseOrderNumberEntity entity){
        if (dto.getOrderNumber() != null){
            entity.setOrderNumber(dto.getOrderNumber());
        }
        if (dto.getNameUz() != null){
            entity.setNameUz(dto.getNameUz());
        }
        if (dto.getNameRu() != null){
            entity.setNameRu(dto.getNameRu());
        }
        if (dto.getNameEng() != null){
            entity.setNameEng(dto.getNameEng());
        }
        return entity;
    }
}
