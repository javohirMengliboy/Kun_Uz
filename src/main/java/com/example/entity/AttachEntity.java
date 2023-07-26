package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "attach")
public class AttachEntity extends BaseStringEntity{
    @Column(name = "original_name")
    private String originalName;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "extension", nullable = false)
    private String extension;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "image")
    private ProfileEntity profileEntity;
}
