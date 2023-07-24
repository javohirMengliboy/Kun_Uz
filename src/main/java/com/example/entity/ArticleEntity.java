package com.example.entity;

import com.example.enums.ArticleStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "shared_count")
    private Integer sharedCount = 120;

    @ManyToOne
    @JoinColumn(name = "attach_id")
    private AttachEntity attachId;

    @ManyToOne
    @JoinColumn (name = "region_id")
    private RegionEntity regionId;

    @ManyToOne
    @JoinColumn (name = "category_id", nullable = false)
    private CategoryEntity categoryId;

    @ManyToOne
    @JoinColumn (name = "moderator_id", nullable = false)
    private ProfileEntity moderatorId;

    @ManyToOne
    @JoinColumn (name = "publisher_id")
    private ProfileEntity publisherId;

    @Enumerated(EnumType.STRING)
    private ArticleStatus status = ArticleStatus.PUBLISHED;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

    @Column(name = "published_date")
    private LocalDateTime publishedDate = LocalDateTime.now().plusDays(2);

    @Column(name = "visible")
    private Boolean visible = true;

    @Column(name = "view_count")
    private Integer viewCount = 60;

    @ManyToMany
    @JoinTable(
            name = "article_types",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "article_type_id")
    )
    private List<ArticleTypeEntity> articleTypes;
}
