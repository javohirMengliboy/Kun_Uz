package com.example.repository;

import com.example.entity.CommentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, String> {
    List<CommentEntity> findAllByArticleId(String articleId);
    List<CommentEntity> findAllByReplayId(Integer replayId);
    List<CommentEntity> findAllByVisibleTrue(Pageable pageable);
}
