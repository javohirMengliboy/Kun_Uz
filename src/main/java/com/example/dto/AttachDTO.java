package com.example.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class AttachDTO {
    private String id;
    private String originalName;
    private String path;
    private Long size;
    private String extension;
    private LocalDateTime createdDate;
    private String url;

    public AttachDTO() {
    }

    public AttachDTO(String id, String originalName, String path, Long size, String extension, LocalDateTime createdDate, String url) {
        this.id = id;
        this.originalName = originalName;
        this.path = path;
        this.size = size;
        this.extension = extension;
        this.createdDate = createdDate;
        this.url = url;
    }
}
