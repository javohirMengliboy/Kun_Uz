package com.example.repository;

import com.example.entity.CategoryEntity;
import com.example.mapper.LanguageI;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Integer> {
    @Transactional
    @Modifying
    @Query("update CategoryEntity set visible = false where id = :id")
    int deleteCategoryById(Integer id);

    @Query(value = "select id, order_number as orderNumber, case :lang when 'uz' then name_uz when 'ru' then name_ru when 'en' then name_eng end as name from category", nativeQuery = true)
    List<LanguageI> getCategoryByLanguage(@Param("lang") String lang);

    Optional<CategoryEntity> findByOrderNumber(Integer orderNumber);

    List<CategoryEntity> findAllByVisibleTrue();
}
