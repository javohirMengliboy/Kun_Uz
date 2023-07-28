package com.example.repository;

import com.example.dto.ArticleDTO;
import com.example.dto.ArticleFilterDTO;
import com.example.dto.FilterResultDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository
public class ArticleCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO filter(ArticleFilterDTO filterDTO, Integer page, Integer size){
        StringBuilder selectBuilder = new StringBuilder();
        selectBuilder.append("select new com.example.dto.ArticleDTO" +
                "(id, visible, createdDate, title, description, content, sharedCount, attachId, regionId, categoryId, moderatorId, publisherId, status, publishedDate, viewCount)" +
                " from ArticleEntity where visible = true and status = PUBLISHED ");
        StringBuilder countBuilder = new StringBuilder("select count(a) from ArticleEntity a where visible = true and status = PUBLISHED");

        Map<String, Object> params = new HashMap<>();

        StringBuilder stringBuilder = new StringBuilder();
        if (filterDTO.getRegionId() != null){
            stringBuilder.append(" and regionId = :regionId");
            params.put("regionId", filterDTO.getRegionId());
        }
        if (filterDTO.getCategoryId() != null){
            stringBuilder.append(" and categoryId = :categoryId");
            params.put("categoryId", filterDTO.getCategoryId());
        }
        if (filterDTO.getCreateFrom() != null && filterDTO.getCreateTo() != null){
            stringBuilder.append(" and createdDate between :createFrom and :createTo");
            params.put("createFrom", LocalDateTime.of(filterDTO.getCreateFrom(), LocalTime.MIN));
            params.put("createTo", LocalDateTime.of(filterDTO.getCreateTo(), LocalTime.MAX));
        }
        if (filterDTO.getPublishFrom() != null && filterDTO.getPublishTo() != null){
            stringBuilder.append(" and updateDate between :publishFrom and :publishTo");
            params.put("publishFrom", LocalDateTime.of(filterDTO.getPublishFrom(), LocalTime.MIN));
            params.put("publishTo", LocalDateTime.of(filterDTO.getPublishTo(), LocalTime.MAX));
        }
        if (filterDTO.getModeratorId() != null){
            stringBuilder.append(" and moderatorId = :moderatorId");
            params.put("moderatorId", filterDTO.getModeratorId());
        }
        if (filterDTO.getPublisherId() != null){
            stringBuilder.append(" and publisherId = :publisherId");
            params.put("publisherId", filterDTO.getPublisherId());
        }
        if (filterDTO.getStatus() != null){
            stringBuilder.append(" and status = :status");
            params.put("status", filterDTO.getStatus());
        }

        selectBuilder.append(stringBuilder).append(" order by publishedDate desc");
        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult(size * page);

        countBuilder.append(stringBuilder);
        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<ArticleDTO> list = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResultDTO(list, totalCount);
    }
}
