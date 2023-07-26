package com.example.service;

import com.example.dto.ArticleTypeDTO;
import com.example.entity.ArticleTypeEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.LanguageMapper;
import com.example.repository.ArticleTypeRepository;
import com.example.util.ServiceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleTypeService {
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    public void setArticleTypeRepository(ArticleTypeRepository articleTypeRepository) {
        this.articleTypeRepository = articleTypeRepository;
    }

    public ArticleTypeDTO create(ArticleTypeDTO dto) {
        Optional<ArticleTypeEntity> optional = articleTypeRepository.findByOrderNumber(dto.getOrderNumber());
        if (optional.isPresent()){
            throw  new AppBadRequestException("This order number already exists");
        }
        ArticleTypeEntity entity = new ArticleTypeEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEng(dto.getNameEng());
        articleTypeRepository.save(entity);
        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(ArticleTypeDTO dto, Integer id) {
        articleTypeRepository.save(checkingForUpdate(dto, get(id)));
        return true;
    }



    public Boolean deleteArticleTypeById(Integer id) {
        get(id);
        int effectedRows = articleTypeRepository.deleteArticleTypeById(id);
        return effectedRows > 0;
    }

    public List<ArticleTypeDTO> getAll() {
        List<ArticleTypeEntity> entityList = articleTypeRepository.findAllByVisibleTrue();
        if(entityList.isEmpty()){
            throw new ItemNotFoundException("Region not found");
        }
        List<ArticleTypeDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    public List<LanguageMapper> getByLanguage(String lang) {
        return ServiceUtil.getByLanguage(articleTypeRepository.getArticleTypeByLanguage(lang));
    }


    private ArticleTypeEntity checkingForUpdate(ArticleTypeDTO dto, ArticleTypeEntity entity) {
        return (ArticleTypeEntity) ServiceUtil.checkingForUpdate(dto, entity);
    }

    private ArticleTypeDTO toDto(ArticleTypeEntity entity) {
        return new ArticleTypeDTO(entity.getId(), entity.getVisible(),
                entity.getCreatedDate(), entity.getOrderNumber(),
                entity.getNameUz(), entity.getNameRu(), entity.getNameEng());
    }

    public ArticleTypeEntity get(Integer typeId) {
        return articleTypeRepository.findById(typeId).orElseThrow(() -> new ItemNotFoundException("ArticleType not found"));
    }
}
