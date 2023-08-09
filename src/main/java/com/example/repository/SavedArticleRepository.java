package com.example.repository;

import com.example.entity.SavedArticleEntity;
import com.example.mapper.ArticleGetMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SavedArticleRepository extends CrudRepository<SavedArticleEntity, String> {
    Optional<SavedArticleEntity> findByArticleId(String articleId);

    @Query("select new com.example.mapper.ArticleGetMapper(a.id, a.title, a.description, a.content) " +
            "from ArticleEntity a where a.id in (select sa.id from SavedArticleEntity sa where sa.profileId = :currentUserId)")
    List<ArticleGetMapper> findArticlesByProfileId(Integer currentUserId);
}
