package com.example.service;

import com.example.entity.ArticleAndTagsEntity;
import com.example.repository.ArticleAndTagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleAndTagsService {
    @Autowired
    private ArticleAndTagsRepository articleAndTagsRepository;

    public void create(String articleId, Integer tagId){
        ArticleAndTagsEntity entity = new ArticleAndTagsEntity();
        entity.setArticleId(articleId);
        entity.setTagId(tagId);
        articleAndTagsRepository.save(entity);
    }
}
