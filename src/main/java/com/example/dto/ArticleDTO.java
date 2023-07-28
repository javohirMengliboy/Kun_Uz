package com.example.dto;

import com.example.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ArticleDTO extends BaseDTO{
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
    private LocalDateTime publishedDate;
    private Integer viewCount;
    private List<Integer> articleTypes;
    private List<Integer> articleTags;



    public ArticleDTO(String id, boolean visible, LocalDateTime createdDate, String title, String description, String content, Integer sharedCount, String attachId, Integer regionId, Integer categoryId, Integer moderatorId, Integer publisherId, ArticleStatus status, LocalDateTime publishedDate, Integer viewCount) {
        super(visible, createdDate);
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
        this.publishedDate = publishedDate;
        this.viewCount = viewCount;
    }

}
