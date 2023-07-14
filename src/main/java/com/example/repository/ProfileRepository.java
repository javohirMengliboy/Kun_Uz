package com.example.repository;

import com.example.dto.ProfileDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = :status where id = :id")
    int updateStatusById(@Param("status") ProfileStatus status, @Param("id") Integer id);

    /** 4 */
    @Query("select new com.example.dto.ProfileDTO(id, name, surname, email, phone, password, status, role, visible, createdDate, photoId) from ProfileEntity")
    List<ProfileDTO> getAll();

    @Transactional
    @Modifying
    @Query("delete from ProfileEntity where id = :id")
    int deleteProfileById(@Param("id") Integer id);
}
