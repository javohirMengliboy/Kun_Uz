package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.ArticleLikeDTO;
import com.example.entity.ArticleLikeEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.LikeStatus;
import com.example.repository.ArticleLikeRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    public ArticleLikeDTO like( ArticleLikeDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        ProfileEntity profile = userDetails.getProfile();
        ArticleLikeEntity entity = hasLiked(dto.getArticleId(), profile.getId());
        if (entity != null){
            if (entity.getStatus() == null){
                entity.setStatus(LikeStatus.LIKE);
            }else {
                entity.setStatus(null);
            }
        }else {
            entity = new ArticleLikeEntity();
            entity.setArticleId(dto.getArticleId());
            entity.setProfileId(profile.getId());
            entity.setStatus(LikeStatus.LIKE);
        }
        articleLikeRepository.save(entity);
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;

    }

    public ArticleLikeDTO dislike(ArticleLikeDTO dto) {
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        ProfileEntity profile = userDetails.getProfile();
        ArticleLikeEntity entity = hasLiked(dto.getArticleId(), profile.getId());
        if (entity != null){
            if (entity.getStatus() == null){
                entity.setStatus(LikeStatus.DISLIKE);
            }else {
                entity.setStatus(null);
            }
        }else {
            entity = new ArticleLikeEntity();
            entity.setArticleId(dto.getArticleId());
            entity.setProfileId(profile.getId());
            entity.setStatus(LikeStatus.DISLIKE);
        }
        articleLikeRepository.save(entity);
        dto.setId(entity.getId());
        dto.setProfileId(entity.getProfileId());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public ArticleLikeEntity hasLiked(String articleId, Integer profileId){
        return articleLikeRepository.findByArticleIdAndProfileId(articleId, profileId);
    }
}
