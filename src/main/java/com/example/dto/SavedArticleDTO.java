package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
public class SavedArticleDTO {
    private String id;
    private Integer profileId;
    private String articleId;
    private LocalDateTime createdDate;

    public SavedArticleDTO() {
    }

    public SavedArticleDTO(String id, Integer profileId, String articleId, LocalDateTime createdDate) {
        this.id = id;
        this.profileId = profileId;
        this.articleId = articleId;
        this.createdDate = createdDate;
    }
}