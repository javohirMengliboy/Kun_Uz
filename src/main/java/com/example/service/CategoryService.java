package com.example.service;

import com.example.dto.CategoryDTO;
import com.example.dto.ProfileDTO;
import com.example.entity.CategoryEntity;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.CategoryLanguageMapper;
import com.example.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        CategoryEntity entity = new CategoryEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        dto.setVisible(entity.isVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean updateOrderById(CategoryDTO dto, Integer id) {
        int effectedRows = categoryRepository.updateOrderById(dto.getOrderNumber(),id);
        return effectedRows > 0;
    }

    public Boolean deleteProfileById(Integer id) {
        int effectedRows = categoryRepository.deleteProfileById(id);
        return effectedRows > 0;
    }

    public List<CategoryDTO> getAll() {
        List<CategoryDTO> dtoList = categoryRepository.getAllCategory();
        if(dtoList.isEmpty()){
            throw new ItemNotFoundException("Category not found");
        }
        return dtoList;
    }

    public List<CategoryLanguageMapper> getByLanguage(String lang) {
        if (lang.toLowerCase().startsWith("uz")){
            return categoryRepository.getUzCategories();
        }else if (lang.toLowerCase().startsWith("ru")){
            return categoryRepository.getRuCategories();
        }else if (lang.toLowerCase().startsWith("en")){
            return categoryRepository.getEngCategories();
        }
        throw new ItemNotFoundException("Not found");
    }
}
