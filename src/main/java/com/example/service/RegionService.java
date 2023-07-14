package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.dto.RegionDTO;
import com.example.entity.CategoryEntity;
import com.example.entity.RegionEntity;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.CategoryLanguageMapper;
import com.example.mapper.RegionLanguageMapper;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDTO create(RegionDTO dto) {
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        regionRepository.save(entity);
        dto.setId(entity.getId());
        dto.setVisible(entity.isVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean updateOrderById(CategoryDTO dto, Integer id) {
        int effectedRows = regionRepository.updateOrderById(dto.getOrderNumber(),id);
        return effectedRows > 0;
    }

    public Boolean deleteProfileById(Integer id) {
        int effectedRows = regionRepository.deleteProfileById(id);
        return effectedRows > 0;
    }

    public List<RegionDTO> getAll() {
        List<RegionDTO> dtoList = regionRepository.getAllCategory();
        if(dtoList.isEmpty()){
            throw new ItemNotFoundException("Category not found");
        }
        return dtoList;
    }

    public List<RegionLanguageMapper> getByLanguage(String lang) {
        if (lang.toLowerCase().startsWith("uz")){
            return regionRepository.getUzCategories();
        }else if (lang.toLowerCase().startsWith("ru")){
            return regionRepository.getRuCategories();
        }else if (lang.toLowerCase().startsWith("en")){
            return regionRepository.getEngCategories();
        }
        throw new ItemNotFoundException("Not found");
    }
}
