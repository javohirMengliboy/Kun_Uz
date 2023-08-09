package com.example.service;

import com.example.config.CustomUserDetails;
import com.example.dto.CommentDTO;
import com.example.dto.CommentFilterDTO;
import com.example.dto.FilterResultDTO;
import com.example.entity.CommentEntity;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.exp.AppBadRequestException;
import com.example.exp.AppMethodNotAllowedException;
import com.example.exp.ItemNotFoundException;
import com.example.repository.CommentCustomRepository;
import com.example.repository.CommentRepository;
import com.example.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentCustomRepository customRepository;

    public CommentDTO create(CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setArticleId(dto.getArticleId());
        entity.setProfileId(SpringSecurityUtil.getCurrentUserId());
        if (dto.getReplayId() != null){
            entity.setReplayId(dto.getReplayId());
        }
        commentRepository.save(entity);
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.getVisible());
        dto.setProfileId(entity.getProfileId());
        if (entity.getReplayId() != null){
            dto.setReplayId(entity.getReplayId());
        }
        dto.setId(entity.getId());
        return dto;
    }

    public CommentDTO update(CommentDTO dto, String commentId) {
        CommentEntity entity = get(commentId);
        if (!entity.getProfileId().equals(SpringSecurityUtil.getCurrentUserId())){
            throw new AppBadRequestException("Not Yours");
        }
        entity.setContent(dto.getContent());
        entity.setUpdateDate(LocalDateTime.now());
        commentRepository.save(entity);
        dto.setUpdateDate(entity.getUpdateDate());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setVisible(entity.getVisible());
        dto.setProfileId(entity.getProfileId());
        if (entity.getReplayId() != null){
            dto.setReplayId(entity.getReplayId());
        }
        dto.setId(entity.getId());
        return dto;

    }

    private CommentEntity get(String commentId) {
        return commentRepository.findById(commentId).orElseThrow(()->new ItemNotFoundException("Comment not found"));
    }


    public Boolean delete(String commentId) {
        CommentEntity comment = get(commentId);
        CustomUserDetails userDetails = SpringSecurityUtil.getCurrentUser();
        ProfileEntity profile = userDetails.getProfile();
        if (profile.getRole().equals(ProfileRole.ROLE_ADMIN) || comment.getProfileId().equals(profile.getId())){
           commentRepository.delete(comment);
           return true;
        }
        throw new AppMethodNotAllowedException();
    }

    public List<CommentDTO> getByArticle(String articleId) {
        List<CommentDTO> dtoList = new ArrayList<>();
        List<CommentEntity> entityList = commentRepository.findAllByArticleId(articleId);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Comments not found");
        }
        entityList.forEach(entity-> dtoList.add(toDTO(entity)));
        return dtoList;

    }

    private CommentDTO toDTO(CommentEntity entity) {
        return new CommentDTO(entity.getId(), entity.getContent(), entity.getReplayId(),
                entity.getProfileId(), entity.getArticleId(), entity.getCreatedDate(),
                entity.getUpdateDate(), entity.getVisible());
    }

    public List<CommentDTO> getByReplay(Integer replayId) {
        List<CommentDTO> dtoList = new ArrayList<>();
        List<CommentEntity> entityList = commentRepository.findAllByReplayId(replayId);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Comments not found");
        }
        entityList.forEach(entity-> dtoList.add(toDTO(entity)));
        return dtoList;
    }

    public List<CommentDTO> pagination(int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        List<CommentDTO> dtoList = new ArrayList<>();
        List<CommentEntity> entityList = commentRepository.findAllByVisibleTrue(pageable);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Comments not found");
        }
        entityList.forEach(entity-> dtoList.add(toDTO(entity)));
        return dtoList;
    }

    public PageImpl<CommentDTO> filter(CommentFilterDTO filterDTO, int page, int size) {
        FilterResultDTO result = customRepository.filter(filterDTO, page-1,  size);
        List<CommentDTO> dtoList = result.getList();
        return new PageImpl<>(dtoList, PageRequest.of(page,size), result.totalCount);
    }
}
