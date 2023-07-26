package com.example.service;

import com.example.dto.ArticleDTO;
import com.example.entity.*;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.ArticleGetMapper;
import com.example.mapper.ArticleMapper;
import com.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class ArticleService {
    private ArticleRepository articleRepository;
    @Autowired
    public void setArticleRepository(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    private AttachService attachService;
    @Autowired
    public void setAttachService(AttachService attachService) {
        this.attachService = attachService;
    }

    private CategoryService categoryService;
    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    private RegionService regionService;
    @Autowired
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    private ProfileService profileService;
    @Autowired
    public void setProfileService(ProfileService profileService) {
        this.profileService = profileService;
    }

    private ArticleTypeService articleTypeService;
    @Autowired
    public void setArticleTypeService(ArticleTypeService articleTypeService) {
        this.articleTypeService = articleTypeService;
    }


    /** 1 */
    public ArticleDTO create(ArticleDTO dto,Integer moderatorId) {
        AttachEntity attach = attachService.get(dto.getAttachId());
        if (attach == null){
            throw new ItemNotFoundException("Attach not found");
        }

        CategoryEntity category = categoryService.get(dto.getCategoryId());
        if (category == null){
            throw new ItemNotFoundException("Category not found");
        }

        RegionEntity region = regionService.get(dto.getRegionId());
        if (region == null){
            throw new ItemNotFoundException("Region not found");
        }

        ProfileEntity moderator = profileService.get(moderatorId);
        if (moderator == null){
            throw new ItemNotFoundException("Moderator not found");
        }
        ArticleEntity entity = new ArticleEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
        entity.setAttachId(dto.getAttachId());
        entity.setRegionId(region.getId());
        entity.setCategoryId(category.getId());
        entity.setModeratorId(moderator.getId());

        articleRepository.save(entity);
        dto.setId(entity.getId());
        dto.setSharedCount(entity.getSharedCount());
        dto.setModeratorId(entity.getModeratorId());
        dto.setPublisherId(entity.getPublisherId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setPublishedDate(entity.getPublishedDate());
        dto.setVisible(entity.getVisible());
        dto.setViewCount(entity.getViewCount());
        return dto;
    }


    /** 2 */
    public Boolean update(ArticleDTO dto, String id) {
        ArticleEntity entity = articleRepository.findById(id).orElseThrow(() ->
                new ItemNotFoundException("Article not found"));
        entity.setStatus(dto.getStatus());
        articleRepository.save(entity);
        return true;
    }

    /** 4 */
    public List<ArticleDTO> getAll() {
        List<ArticleEntity> entityList = articleRepository.findAllByVisibleTrue();
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Article not found");
        }
        List<ArticleDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    private ArticleDTO toDto(ArticleEntity entity) {
        return new ArticleDTO(entity.getId(), entity.getVisible(), entity.getCreatedDate(),
                entity.getTitle(), entity.getDescription(), entity.getContent(), entity.getSharedCount(),
                entity.getAttachId(), entity.getRegionId(), entity.getCategoryId(), entity.getModeratorId(),
                entity.getPublisherId(), entity.getStatus(), entity.getPublishedDate(), entity.getViewCount());
    }

    /** 5 */
    public Boolean deleteProfileById(String id) {
        System.out.println("Id ="+id);
        int effectedRows = articleRepository.deleteArticleById(id);
        return effectedRows > 0;
    }

    public List<ArticleDTO> getByType(Integer typeId, Integer limit) {
        List<ArticleDTO> dtoList = new ArrayList<>();
        List<ArticleEntity> entityList = articleRepository.getByType5(typeId, limit);
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }

    public List<ArticleDTO> getIDNotIncluded(List<String> idList) {
        List<ArticleDTO> dtoList = new ArrayList<>();
        List<ArticleEntity> entityList = articleRepository.getIDNotIncluded(idList);
        if (entityList.isEmpty()){
            throw new AppBadRequestException("Articles not found");
        }
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;

    }

    public ArticleDTO getById(String id) {
        return toDto(articleRepository.findById(id).orElseThrow(() ->new ItemNotFoundException("Article not found")));
    }

    public List<ArticleGetMapper> getByTypeNotId(String id, Integer type) {
        ArticleTypeEntity typeEntity = articleTypeService.get(type);

        List<ArticleMapper> mapperList = articleRepository.getByTypeNotId(id,type);
        if (mapperList.isEmpty()){
            throw new AppBadRequestException("Articles not found");
        }
        List<ArticleGetMapper> getMappers = new ArrayList<>();
        mapperList.forEach(mapper ->{
            ArticleGetMapper getMapper = new ArticleGetMapper(mapper.getId(), mapper.getTitle(), mapper.getDescription(), mapper.getContent());
            getMappers.add(getMapper);
        });
        return getMappers;
    }

    public ArticleEntity get(String articleId) {
        return articleRepository.findById(articleId).orElseThrow(() -> new ItemNotFoundException("Article not found"));
    }
}
