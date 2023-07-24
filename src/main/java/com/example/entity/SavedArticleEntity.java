package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@Entity
@Table(name = "saved_article")
public final class SavedArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(columnDefinition = "profile_id")
    private ProfileEntity profileId;

    @ManyToOne
    @JoinColumn(columnDefinition = "article_id")
    private ArticleEntity articleId;

    @JoinColumn(columnDefinition = "created_date")
    private LocalDateTime createdDate;
}