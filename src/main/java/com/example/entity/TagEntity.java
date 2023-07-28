package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tag")
public class TagEntity extends BaseSequenceEntity{
    @Column(name = "order_number")
    private Integer orderNumber;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag")
    private List<ArticleAndTagsEntity> articleAndTagsEntities;
}
