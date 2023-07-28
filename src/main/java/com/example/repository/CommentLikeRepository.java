package com.example.repository;

import com.example.entity.CommentLikeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends CrudRepository<CommentLikeEntity, Integer> {
    CommentLikeEntity findByCommentIdAndProfileId(Integer commentId, Integer profileId);
}
