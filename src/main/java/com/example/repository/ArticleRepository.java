package com.example.repository;

import com.example.entity.ArticleEntity;
import com.example.entity.ArticleTypeEntity;
import com.example.mapper.ArticleGetMapper;
import com.example.mapper.ArticleMapper;
import org.springframework.data.domain.Pageable;
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

    @Query("from ArticleEntity a inner join a.articleAndTypesEntities at where a.visible = true and a.status = PUBLISHED and at.id = :typeId order by a.publishedDate limit :limit")
    List<ArticleEntity> getByType5(@Param("typeId") Integer typeId, @Param("limit") Integer limit);

    @Query("from ArticleEntity  where visible = true and status = 'PUBLISHED' and id not in :idList order by publishedDate limit 8")
    List<ArticleEntity> getIDNotIncluded(@Param("idList") List<String> idList);

    @Query(value = "select a.id, a.title, a.description, a.content from article as a inner join article_and_types as atp on a.id = atp.article_id where a.visible = true and a.status = 'PUBLISHED' and a.id != :id and atp.article_type_id = :type order by a.published_date limit 4", nativeQuery = true)
    List<ArticleMapper> getByTypeNotId(String id, Integer type);
    @Query(value = "select id, title, description, content from article where visible = true and status = 'PUBLISHED' order by view_count desc limit 4", nativeQuery = true)
    List<ArticleMapper> getFourMostRead();

    @Query(value = "select a.id, a.title, a.description, a.content from article as a inner join article_and_types as atp on a.id = atp.article_id where a.visible = true and a.status = 'PUBLISHED' and a.region_id = :regionId and atp.article_type_id = :typeId order by a.published_date limit 5", nativeQuery = true)
    List<ArticleMapper> getByTypeAndRegion(Integer typeId, Integer regionId);

    @Query(value = "select id, title, description, content from article where visible = true and status = 'PUBLISHED' and region_id = :regionId order by published_date desc offset :page limit :size", nativeQuery = true)
    List<ArticleMapper> getPaginationByRegion(Integer regionId, Integer page, Integer size);

    @Query(value = "select id, title, description, content from article where visible = true and status = 'PUBLISHED' and category_id = :categoryId order by published_date desc limit 5", nativeQuery = true)
    List<ArticleMapper> getLastFiveByCategory(Integer categoryId);

    @Query(value = "select id, title, description, content from article where visible = true and status = 'PUBLISHED' and category_id = :categoryId order by published_date desc offset :page limit :size", nativeQuery = true)
    List<ArticleMapper> getPaginationByCategory(Integer categoryId, int page, int size);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set viewCount = viewCount+1 where id = :id")
    int increaseViewCount(String id);

    @Transactional
    @Modifying
    @Query("update ArticleEntity set sharedCount = sharedCount+1 where id = :id")
    int increaseShareCount(String id);

    @Query(value = "select new com.example.mapper.ArticleGetMapper(a.id,a.title,a.description,a.content) from ArticleEntity a inner join a.articleAndTagsEntities at where a.visible = true and a.status = 'PUBLISHED' and at.tagId = :tagId order by a.publishedDate desc limit 4")
    List<ArticleGetMapper> getLasFourByTag(int tagId);
}
