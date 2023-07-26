package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "article_and_types")
public class ArticleAndTypesEntity extends BaseStringEntity{
    @Column(name = "article_id")
    private String articleId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", insertable = false,updatable = false)
    private ArticleEntity article;

    @Column(name = "article_type_id")
    private Integer articleTypeId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_type_id", insertable = false,updatable = false)
    private ArticleTypeEntity articleType;
}
