package com.example.service;

import com.example.dto.TagDTO;
import com.example.entity.TagEntity;
import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {
    @Autowired
    private TagRepository tagRepository;

    public TagDTO create(TagDTO dto) {
        Optional<TagEntity> optional = tagRepository.findByOrderNumber(dto.getOrderNumber());
        if (optional.isPresent()){
            throw  new AppBadRequestException("This order number already exists");
        }
        TagEntity entity = new TagEntity();
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setName(dto.getName());
        tagRepository.save(entity);
        dto.setId(entity.getId());
        dto.setVisible(entity.getVisible());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean update(TagDTO dto, Integer id) {
        tagRepository.save(checkingForUpdate(dto, get(id)));
        return true;
    }

    public Boolean deleteTagById(Integer id) {
        get(id);
        int effectedRows = tagRepository.deleteTagById(id);
        return effectedRows > 0;
    }

    public List<TagDTO> getAll() {
        List<TagEntity> entityList = tagRepository.findAllByVisibleTrue();
        if(entityList.isEmpty()){
            throw new ItemNotFoundException("Tag not found");
        }
        List<TagDTO> dtoList = new ArrayList<>();
        entityList.forEach(entity -> dtoList.add(toDto(entity)));
        return dtoList;
    }



    private TagEntity checkingForUpdate(TagDTO dto, TagEntity entity) {
        if (dto.getName() != null){
            entity.setName(dto.getName());
        }
        if (dto.getOrderNumber() != null){
            entity.setOrderNumber(dto.getOrderNumber());
        }
        return entity;
    }

    private TagDTO toDto(TagEntity entity) {
        return new TagDTO(entity.getId(),entity.getOrderNumber(),
                entity.getName(), entity.getVisible(), entity.getCreatedDate());
    }

    public TagEntity get(Integer typeId) {
        return tagRepository.findById(typeId).orElseThrow(() -> new ItemNotFoundException("ArticleType not found"));
    }
}
