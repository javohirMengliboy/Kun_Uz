package com.example.repository;

import com.example.entity.ArticleAndTypesEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleAndTypesRepository extends CrudRepository<ArticleAndTypesEntity, String> {
}
