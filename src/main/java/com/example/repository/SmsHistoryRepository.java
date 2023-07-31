package com.example.repository;

import com.example.entity.SmsHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SmsHistoryRepository extends CrudRepository<SmsHistoryEntity, String> {
    Optional<SmsHistoryEntity> findByPhone(String email);

    List<SmsHistoryEntity> findByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

    Page<SmsHistoryEntity> findAllBy(Pageable pageable);
}
