package com.example.dto;

import com.example.entity.*;
import com.example.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ArticleDTO {
    private String id;
    private String title;
    private String description;
    private String content;
    private Integer sharedCount;
    private String attachId;
    private Integer regionId;
    private Integer categoryId;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;
    private LocalDateTime createdDate = LocalDateTime.now();
    private LocalDateTime publishedDate;
    private Boolean visible;
    private Integer viewCount;
    private List<Integer> articleTypes;

    public ArticleDTO() {
    }

    public ArticleDTO(String id, String title, String description, String content, Integer sharedCount, Integer regionId, Integer categoryId, Integer moderatorId, Integer publisherId, ArticleStatus status, LocalDateTime createdDate, LocalDateTime publishedDate, Boolean visible, Integer viewCount, List<Integer> articleTypes) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.sharedCount = sharedCount;
        this.regionId = regionId;
        this.categoryId = categoryId;
        this.moderatorId = moderatorId;
        this.publisherId = publisherId;
        this.status = status;
        this.createdDate = createdDate;
        this.publishedDate = publishedDate;
        this.visible = visible;
        this.viewCount = viewCount;
        this.articleTypes = articleTypes;
    }

    public ArticleDTO(String id, String title, String description, String content, Integer sharedCount, String attachId, Integer regionId, Integer categoryId, Integer moderatorId, Integer publisherId, ArticleStatus status, LocalDateTime createdDate, LocalDateTime publishedDate, Boolean visible, Integer viewCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.sharedCount = sharedCount;
        this.attachId = attachId;
        this.regionId = regionId;
        this.categoryId = categoryId;
        this.moderatorId = moderatorId;
        this.publisherId = publisherId;
        this.status = status;
        this.createdDate = createdDate;
        this.publishedDate = publishedDate;
        this.visible = visible;
        this.viewCount = viewCount;
    }
}
