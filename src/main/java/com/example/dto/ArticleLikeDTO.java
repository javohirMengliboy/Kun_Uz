package com.example.dto;

import com.example.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class ArticleLikeDTO {
    private String id;
    private Integer profileId;
    private String articleId;
    private LocalDateTime createdDate;
    private LikeStatus status;

    public ArticleLikeDTO() {
    }

    public ArticleLikeDTO(String id, Integer profileId, String articleId, LocalDateTime createdDate, LikeStatus status) {
        this.id = id;
        this.profileId = profileId;
        this.articleId = articleId;
        this.createdDate = createdDate;
        this.status = status;
    }
}
