package com.example.repository;

import com.example.entity.TagEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Integer> {
    Optional<TagEntity> findByOrderNumber(Integer orderNumber);

    @Transactional
    @Modifying
    @Query("update TagEntity set visible = false where id = :id")

    int deleteTagById(Integer id);

    List<TagEntity> findAllByVisibleTrue();
}
