package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "content")
    private String content;

    @Column(name = "replay_id")
    private String replayId;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profileId;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private ArticleEntity articleId;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "visible")
    private Boolean visible;
}
