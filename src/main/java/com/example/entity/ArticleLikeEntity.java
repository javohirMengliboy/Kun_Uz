package com.example.entity;

import com.example.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "article_like")
public final class ArticleLikeEntity {
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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LikeStatus status;
}

