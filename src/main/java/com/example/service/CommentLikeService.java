package com.example.service;

import com.example.dto.CommentLikeDTO;
import com.example.entity.CommentLikeEntity;
import com.example.enums.LikeStatus;
import com.example.repository.CommentLikeRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeService {
    @Autowired
    private CommentLikeRepository commentLikeRepository;

    public CommentLikeDTO like(CommentLikeDTO dto) {
        Integer profileId = SpringSecurityUtil.getCurrentUserId();
        CommentLikeEntity entity = hasLiked(dto.getCommentId(),profileId);
        if (entity != null){
            if (entity.getStatus() == null){
                entity.setStatus(LikeStatus.LIKE);
            }else {
                entity.setStatus(null);
            }
        }else {
            entity = new CommentLikeEntity();
            entity.setCommentId(dto.getCommentId());
            entity.setProfileId(profileId);
            entity.setStatus(LikeStatus.LIKE);
        }
        commentLikeRepository.save(entity);
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;

    }

    public CommentLikeDTO dislike(CommentLikeDTO dto) {
        Integer profileId = SpringSecurityUtil.getCurrentUserId();
        CommentLikeEntity entity = hasLiked(dto.getCommentId(),profileId);
        if (entity != null){
            if (entity.getStatus() == null){
                entity.setStatus(LikeStatus.DISLIKE);
            }else {
                entity.setStatus(null);
            }
        }else {
            entity = new CommentLikeEntity();
            entity.setCommentId(dto.getCommentId());
            entity.setProfileId(profileId);
            entity.setStatus(LikeStatus.DISLIKE);
        }
        commentLikeRepository.save(entity);
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;

    }

    public CommentLikeEntity hasLiked(Integer commentId, Integer profileId){
        return commentLikeRepository.findByCommentIdAndProfileId(commentId, profileId);
    }
}
