package com.example.entity;

import com.example.enums.LikeStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "comment_like")
public final class CommentLikeEntity extends BaseStringEntity{
    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "comment_id")
    private String commentId;
    @ManyToOne
    @JoinColumn(name = "article_id", insertable = false, updatable = false)
    private CommentEntity comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private LikeStatus status;
}
