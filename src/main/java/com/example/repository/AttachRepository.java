package com.example.repository;

import com.example.entity.AttachEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachRepository extends CrudRepository<AttachEntity, String> {

    @Query("from AttachEntity")
    List<AttachEntity> findAllAttach(Pageable pageable);
}
