package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.LanguageI;
import com.example.mapper.LanguageMapper;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryDTO create(CategoryDTO dto) {
        Optional<CategoryEntity> optional = categoryRepository.findByOrderNumber(dto.getOrderNumber());
        if (optional.isPresent()){
            throw  new AppBadRequestException("This order number already exists");
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(CategoryDTO dto, Integer id) {
        categoryRepository.save(checkingForUpdate(dto, get(id)));
        return true;
    }


    public Boolean deleteCategoryById(Integer id) {
        categoryRepository.findById(id).orElseThrow(() -> new AppBadRequestException("Category not found"));
        int effectedRows = categoryRepository.deleteCategoryById(id);
        return effectedRows > 0;
    }

    public List<CategoryDTO> getAll() {
        List<CategoryEntity> entityList = categoryRepository.findAllByVisibleTrue();
        if(entityList.isEmpty()){
            throw new ItemNotFoundException("Category not found");
        }
        List<CategoryDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    public List<LanguageMapper> getByLanguage(String lang) {
        List<LanguageI> list = categoryRepository.getCategoryByLanguage(lang);
        List<LanguageMapper> mapperList = new ArrayList<>();
        list.forEach(entity -> {
            LanguageMapper mapper = new LanguageMapper();
            mapper.setId(entity.getId());
            mapper.setOrderNumber(entity.getOrderNumber());
            mapper.setName(entity.getName());
            mapperList.add(mapper);
        });
        return mapperList;    }




    private CategoryEntity checkingForUpdate(CategoryDTO dto, CategoryEntity entity) {
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

    private CategoryDTO toDto(CategoryEntity entity) {
        return new CategoryDTO(entity.getId(), entity.getOrderNumber(),
                entity.getNameUz(), entity.getNameRu(), entity.getNameRu(),
                entity.getVisible(), entity.getCreatedDate());
    }

    public CategoryEntity get(Integer categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() -> new ItemNotFoundException("Category not found"));
    }
}
