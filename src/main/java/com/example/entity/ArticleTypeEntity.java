package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "article_type")
public class ArticleTypeEntity extends BaseOrderNumberEntity{
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "articleType")
    private List<ArticleAndTypesEntity> articleAndTypesEntities;
}
