package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleAndTypeDTO {
    private String id;
    private String articleId;
    private Integer articleTypeId;

    public ArticleAndTypeDTO() {
    }

    public ArticleAndTypeDTO(String id, String articleId, Integer articleTypeId) {
        this.id = id;
        this.articleId = articleId;
        this.articleTypeId = articleTypeId;
    }
}
