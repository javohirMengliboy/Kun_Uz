package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.entity.ArticleTypeEntity;
import com.example.mapper.ArticleMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ArticleRepository extends CrudRepository<ArticleEntity, String> {
    List<ArticleEntity> findAllByVisibleTrue();

    @Transactional
    @Modifying
    @Query("update ArticleEntity set visible = false where id = :id")
    int deleteArticleById(@Param("id") String id);

    @Query("from ArticleEntity a inner join a.articleAndTypesEntities at  where at.id = :typeId order by a.publishedDate limit :limit")
    List<ArticleEntity> getByType5(@Param("typeId") Integer typeId, @Param("limit") Integer limit);

    @Query("from ArticleEntity  where id not in :idList order by publishedDate limit 8")
    List<ArticleEntity> getIDNotIncluded(@Param("idList") List<String> idList);

    @Query(value = "select a.id, a.title, a.description, a.content from article as a inner join article_and_types as atp on a.id = atp.article_id where a.id != :id and atp.article_type_id = :type order by a.published_date limit 4", nativeQuery = true)
    List<ArticleMapper> getByTypeNotId(String id, Integer type);
}
