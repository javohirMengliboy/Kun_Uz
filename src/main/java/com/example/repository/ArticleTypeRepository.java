package com.example.repository;

import com.example.entity.ArticleTypeEntity;
import com.example.mapper.LanguageI;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleTypeRepository extends CrudRepository<ArticleTypeEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update ArticleTypeEntity set visible = false where id = :id")
    int deleteArticleTypeById(Integer id);

    @Query(value = "select id, order_number as orderNumber, case :lang when 'uz' then name_uz when 'ru' then name_ru when 'en' then name_eng end as name from article_type", nativeQuery = true)
    List<LanguageI> getRegionByLanguage(@Param("lang") String lang);

    Optional<ArticleTypeEntity> findByOrderNumber(Integer orderNumber);

    List<ArticleTypeEntity> findAllByVisibleTrue();
}
