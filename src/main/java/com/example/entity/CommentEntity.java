package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class
CommentEntity extends BaseIdentityEntity {
    @Column(name = "content")
    private String content;

    @Column(name = "replay_id")
    private String replayId;
    @ManyToOne
    @JoinColumn(name = "replay_id", insertable = false, updatable = false)
    private CommentEntity replayComment;

    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id",insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "article_id")
    private String articleId;
    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private ArticleEntity article;

    @JoinColumn(name = "update_date")
    private LocalDateTime updateDate;

}
