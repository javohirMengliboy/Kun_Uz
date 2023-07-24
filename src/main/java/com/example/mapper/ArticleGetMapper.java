package com.example.mapper;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ArticleGetMapper {
    private String id;
    private String title;
    private String description;
    private String content;

    public ArticleGetMapper(String id, String title, String description, String content) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
    }
}
