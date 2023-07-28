package com.example.service;

import com.example.entity.ArticleAndTypesEntity;
import com.example.repository.ArticleAndTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleAndTypesService {
    @Autowired
    private ArticleAndTypesRepository articleAndTypesRepository;

    public void create(String articleId, Integer typeId){
        ArticleAndTypesEntity entity = new ArticleAndTypesEntity();
        entity.setArticleId(articleId);
        entity.setArticleTypeId(typeId);
        articleAndTypesRepository.save(entity);
    }
}
