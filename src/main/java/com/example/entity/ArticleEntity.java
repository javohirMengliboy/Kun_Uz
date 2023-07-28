package com.example.entity;

import com.example.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity extends BaseStringEntity{
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount = 65;

    @Column(name = "attach_id")
    private String attachId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column (name = "region_id")
    private Integer regionId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "region_id", insertable = false, updatable = false)
    private RegionEntity region;

    @Column (name = "category_id")
    private Integer categoryId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column (name = "moderator_id")
    private Integer moderatorId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "moderator_id", insertable = false, updatable = false)
    private ProfileEntity moderator;

    @Column (name = "publisher_id")
    private Integer publisherId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "moderator_id", insertable = false, updatable = false)
    private ProfileEntity publisher;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status = ArticleStatus.PUBLISHED;

    @Column(name = "published_date")
    private LocalDateTime publishedDate = LocalDateTime.now().plusDays(2);

    @Column(name = "view_count")
    private Integer viewCount = 150;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    private List<ArticleAndTypesEntity> articleAndTypesEntities;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "article")
    private List<ArticleAndTagsEntity> articleAndTagsEntities;
}
