package com.example.entity;

import com.example.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment_like")
public final class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(columnDefinition = "profile_id")
    private ProfileEntity profileId;

    @ManyToOne
    @JoinColumn(columnDefinition = "article_id")
    private CommentEntity articleId;

    @JoinColumn(columnDefinition = "created_date")
    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LikeStatus status;
}
