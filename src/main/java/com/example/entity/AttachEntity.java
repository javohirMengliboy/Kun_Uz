package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity {
    @Id
    private String id;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "extension", nullable = false)
    private String extension;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
}
