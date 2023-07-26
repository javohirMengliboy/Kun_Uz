package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "saved_article")
public final class SavedArticleEntity extends BaseStringEntity{
    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(columnDefinition = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;
}