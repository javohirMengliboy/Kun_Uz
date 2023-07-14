package com.example.repository;

import com.example.dto.CategoryDTO;
import com.example.dto.RegionDTO;
import com.example.entity.RegionEntity;
import com.example.mapper.CategoryLanguageMapper;
import com.example.mapper.RegionLanguageMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update RegionEntity set orderNumber = :orderNumber where id = :id")
    int updateOrderById(@Param("orderNumber") Integer orderNumber, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("delete from RegionEntity where id = :id")
    int deleteProfileById(Integer id);

    @Query("select new com.example.dto.RegionDTO(id, orderNumber, nameUz, nameRu, nameEng, visible, createdDate) from RegionEntity order by orderNumber")
    List<RegionDTO> getAllCategory();

    @Query("select new com.example.mapper.RegionLanguageMapper(id, orderNumber, nameUz) from RegionEntity")
    List<RegionLanguageMapper> getUzCategories();

    @Query("select new com.example.mapper.RegionLanguageMapper(id, orderNumber, nameRu) from RegionEntity")
    List<RegionLanguageMapper> getRuCategories();

    @Query("select new com.example.mapper.RegionLanguageMapper(id, orderNumber, nameEng) from RegionEntity")
    List<RegionLanguageMapper> getEngCategories();
}
