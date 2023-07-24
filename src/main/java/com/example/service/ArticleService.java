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
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private ProfileCustomRepository profileCustomRepository;
    /** 1 */
    public ArticleDTO create(ArticleDTO dto,Integer moderatorId) {
//        Optional<AttachEntity> optionalAttach = attachRepository.findById(dto.getAttachId());
//        if (optionalAttach.isEmpty()){
//            throw new ItemNotFoundException("Attach not found");
//        }

        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (optionalCategory.isEmpty()){
            throw new ItemNotFoundException("Category not found");
        }

        Optional<RegionEntity> optionalRegion = regionRepository.findById(dto.getRegionId());
        if (optionalRegion.isEmpty()){
            throw new ItemNotFoundException("Region not found");
        }

        Optional<ProfileEntity> optionalModerator = profileRepository.findById(moderatorId);
        if (optionalModerator.isEmpty()){
            throw new ItemNotFoundException("Moderator not found");
        }
        ArticleEntity entity = new ArticleEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setContent(dto.getContent());
//        entity.setAttachId(optionalAttach.get());
        entity.setRegionId(optionalRegion.get());
        entity.setCategoryId(optionalCategory.get());
        entity.setModeratorId(optionalModerator.get());
        List<ArticleTypeEntity> allType = new ArrayList<>();
        for (Integer i : dto.getArticleTypes()){
            Optional<ArticleTypeEntity> optional = articleTypeRepository.findById(i);
            if (optional.isEmpty()){
                throw new ItemNotFoundException(i+" id type not found");
            }
            allType.add(optional.get());
        }

        entity.setArticleTypes(allType);
        articleRepository.save(entity);
        dto.setId(entity.getId());
        dto.setSharedCount(entity.getSharedCount());
        dto.setModeratorId(entity.getModeratorId().getId());
//        dto.setPublisherId(entity.getPublisherId().getId());
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
        return new ArticleDTO(entity.getId(), entity.getTitle(), entity.getDescription(),
                entity.getContent(), entity.getSharedCount(), entity.getRegionId().getId(),
                entity.getCategoryId().getId(), entity.getModeratorId().getId(), entity.getPublisherId().getId(), entity.getStatus(),
                entity.getCreatedDate(),entity.getPublishedDate(),entity.getVisible(), entity.getViewCount(),
                entity.getArticleTypes().stream().map(ArticleTypeEntity::getId).collect(Collectors.toList()));
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
        ArticleTypeEntity typeEntity = articleTypeRepository.findById(type).orElseThrow(()-> new ItemNotFoundException("Type not found"));

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
}
