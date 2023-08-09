package com.example.service;

import com.example.dto.ArticleDTO;
import com.example.dto.SavedArticleDTO;
import com.example.entity.SavedArticleEntity;
import com.example.exp.ItemNotFoundException;
import com.example.mapper.ArticleGetMapper;
import com.example.repository.SavedArticleRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SavedArticleService {
    @Autowired
    private SavedArticleRepository savedArticleRepository;

    public SavedArticleDTO create(SavedArticleDTO dto) {
        SavedArticleEntity entity = new SavedArticleEntity();
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(SpringSecurityUtil.getCurrentUserId());
        savedArticleRepository.save(entity);
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean delete(String articleId) {
        SavedArticleEntity entity = savedArticleRepository.findByArticleId(articleId).orElseThrow(()->new ItemNotFoundException("Article not found"));
        savedArticleRepository.delete(entity);
        return true;
    }

    public List<ArticleGetMapper> getList() {
        List<ArticleGetMapper> mapperList = savedArticleRepository.findArticlesByProfileId(SpringSecurityUtil.getCurrentUserId());
        if (mapperList.isEmpty()){
            throw new ItemNotFoundException("Article not found");
        }
        return mapperList;
    }
}
