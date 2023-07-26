package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class CommentFilterDTO {
    private String replayId;
    private Integer profileId;
    private String articleId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private LocalDate updateFrom;
    private LocalDate updateTo;
    public CommentFilterDTO() {
    }


}
