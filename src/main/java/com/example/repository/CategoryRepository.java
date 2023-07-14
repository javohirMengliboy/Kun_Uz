package com.example.repository;

import com.example.dto.CategoryDTO;
import com.example.entity.CategoryEntity;
import com.example.mapper.CategoryLanguageMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update CategoryEntity set orderNumber = :orderNumber where id = :id")
    int updateOrderById(@Param("orderNumber") Integer orderNumber, @Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("delete from CategoryEntity where id = :id")
    int deleteProfileById(Integer id);

    @Query("select new com.example.dto.CategoryDTO(id, orderNumber, nameUz, nameRu, nameEng, visible, createdDate) from CategoryEntity order by orderNumber")
    List<CategoryDTO> getAllCategory();

    @Query("select new com.example.mapper.CategoryLanguageMapper(id, orderNumber, nameUz) from CategoryEntity")
    List<CategoryLanguageMapper> getUzCategories();

    @Query("select new com.example.mapper.CategoryLanguageMapper(id, orderNumber, nameRu) from CategoryEntity")
    List<CategoryLanguageMapper> getRuCategories();

    @Query("select new com.example.mapper.CategoryLanguageMapper(id, orderNumber, nameEng) from CategoryEntity")
    List<CategoryLanguageMapper> getEngCategories();
}
