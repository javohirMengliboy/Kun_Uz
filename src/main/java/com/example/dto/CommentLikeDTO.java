package com.example.dto;

import com.example.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentLikeDTO {
    private String id;
    private Integer profileId;
    private String commentId;
    private LocalDateTime createdDate;
    private LikeStatus status;

    public CommentLikeDTO() {
    }

    public CommentLikeDTO(String id, Integer profileId, String commentId, LocalDateTime createdDate, LikeStatus status) {
        this.id = id;
        this.profileId = profileId;
        this.commentId = commentId;
        this.createdDate = createdDate;
        this.status = status;
    }
}