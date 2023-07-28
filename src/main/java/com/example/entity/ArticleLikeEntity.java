package com.example.entity;

import com.example.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "article_like")
public final class ArticleLikeEntity extends BaseIdentityEntity{
    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LikeStatus status;
}

