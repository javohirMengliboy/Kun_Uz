package com.example.service;
import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.LanguageI;
import com.example.mapper.LanguageMapper;
import com.example.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegionService {
    private RegionRepository regionRepository;
    @Autowired
    public void setRegionRepository(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public RegionDTO create(RegionDTO dto) {
        Optional<RegionEntity> optional = regionRepository.findByOrderNumber(dto.getOrderNumber());
        if (optional.isPresent()){
            throw  new AppBadRequestException("This order number already exists");
        }
        RegionEntity entity = new RegionEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        regionRepository.save(entity);
        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(RegionDTO dto, Integer id) {
        regionRepository.save(checkingForUpdate(dto, get(id)));
        return true;
    }



    public Boolean deleteRegionById(Integer id) {
        get(id);
        int effectedRows = regionRepository.deleteRegionById(id);
        return effectedRows > 0;
    }

    public List<RegionDTO> getAll() {
        List<RegionEntity> entityList = regionRepository.findAllByVisibleTrue();
        if(entityList.isEmpty()){
            throw new ItemNotFoundException("Region not found");
        }
        List<RegionDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }



    public List<LanguageMapper> getByLanguage(String lang) {
        List<LanguageI> list = regionRepository.getRegionByLanguage(lang);
        List<LanguageMapper> mapperList = new ArrayList<>();
        list.forEach(entity -> {
            LanguageMapper mapper = new LanguageMapper();
            mapper.setId(entity.getId());
            mapper.setOrderNumber(entity.getOrderNumber());
            mapper.setName(entity.getName());
            mapperList.add(mapper);
        });
        return mapperList;    }


    private RegionEntity checkingForUpdate(RegionDTO dto, RegionEntity entity) {
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

    private RegionDTO toDto(RegionEntity entity) {
        return new RegionDTO(entity.getId(), entity.getOrderNumber(),
                entity.getNameUz(), entity.getNameRu(), entity.getNameRu(),
                entity.getVisible(), entity.getCreatedDate());
    }

    public RegionEntity get(Integer regionId) {
       return regionRepository.findById(regionId).orElseThrow(() -> new ItemNotFoundException("Region not found"));
    }
}
