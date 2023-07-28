package com.example.dto;

import com.example.enums.ArticleStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class ArticleFilterDTO {
    private String id;
    private String title;
    private Integer regionId;
    private Integer categoryId;
    private LocalDate createFrom;
    private LocalDate createTo;
    private LocalDate publishFrom;
    private LocalDate publishTo;
    private Integer moderatorId;
    private Integer publisherId;
    private ArticleStatus status;

    public ArticleFilterDTO() {
    }

    public ArticleFilterDTO(String id, String title, Integer regionId, Integer categoryId, LocalDate createFrom, LocalDate createTo, LocalDate publishFrom, LocalDate publishTo, Integer moderatorId, Integer publisherId, ArticleStatus status) {
        this.id = id;
        this.title = title;
        this.regionId = regionId;
        this.categoryId = categoryId;
        this.createFrom = createFrom;
        this.createTo = createTo;
        this.publishFrom = publishFrom;
        this.publishTo = publishTo;
        this.moderatorId = moderatorId;
        this.publisherId = publisherId;
        this.status = status;
    }
}
