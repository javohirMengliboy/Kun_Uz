package com.example.repository;

import com.example.entity.ArticleAndTagsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleAndTagsRepository extends CrudRepository<ArticleAndTagsEntity, String> {
}
