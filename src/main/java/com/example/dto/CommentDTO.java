package com.example.dto;

import com.example.enums.LikeStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Integer id;
    private String content;
    private String replayId;
    private Integer profileId;
    private String articleId;
    private LocalDateTime createdDate;
    private LocalDateTime updateDate;
    private Boolean visible;

    public CommentDTO() {
    }

    public CommentDTO(Integer id, String content, String replayId, Integer profileId, String articleId, LocalDateTime createdDate, LocalDateTime updateDate, Boolean visible) {
        this.id = id;
        this.content = content;
        this.replayId = replayId;
        this.profileId = profileId;
        this.articleId = articleId;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.visible = visible;
    }
}
