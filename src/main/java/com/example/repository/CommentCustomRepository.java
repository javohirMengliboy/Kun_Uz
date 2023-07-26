package com.example.repository;

import com.example.dto.*;
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
public class CommentCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO filter(CommentFilterDTO filterDTO, Integer page, Integer size){
        StringBuilder selectBuilder = new StringBuilder();
        selectBuilder.append("select new com.example.dto.CommentDTO" +
                "(id, content, replayId, profileId, articleId, createdDate, updateDate, visible)" +
                " from CommentEntity  where 1 = 1");
        StringBuilder countBuilder = new StringBuilder("select count(p) from CommentEntity p where 1 = 1");

        Map<String, Object> params = new HashMap<>();

        StringBuilder stringBuilder = new StringBuilder();
        if (filterDTO.getReplayId() != null){
            stringBuilder.append(" and replayId = :replayId");
            params.put("replayId", filterDTO.getReplayId());
        }
        if (filterDTO.getProfileId() != null){
            stringBuilder.append(" and profileId = :profileId");
            params.put("profileId", filterDTO.getProfileId());
        }
        if (filterDTO.getArticleId() != null){
            stringBuilder.append(" and articleId = :articleId");
            params.put("articleId", filterDTO.getArticleId());
        }

        if (filterDTO.getDateFrom() != null && filterDTO.getDateTo() != null){
            stringBuilder.append(" and createdDate between :from and :to");
            params.put("from", LocalDateTime.of(filterDTO.getDateFrom(), LocalTime.MIN));
            params.put("to", LocalDateTime.of(filterDTO.getDateTo(), LocalTime.MAX));
        }
        if (filterDTO.getUpdateFrom() != null && filterDTO.getUpdateTo() != null){
            stringBuilder.append(" and updateDate between :updateFrom and :updateTo");
            params.put("updateFrom", LocalDateTime.of(filterDTO.getUpdateFrom(), LocalTime.MIN));
            params.put("updateTo", LocalDateTime.of(filterDTO.getUpdateTo(), LocalTime.MAX));
        }

        selectBuilder.append(stringBuilder).append(" order by createdDate desc");
        Query selectQuery = entityManager.createQuery(selectBuilder.toString());
        selectQuery.setMaxResults(size);
        selectQuery.setFirstResult(size * page);

        countBuilder.append(stringBuilder);
        Query countQuery = entityManager.createQuery(countBuilder.toString());

        for (Map.Entry<String, Object> param : params.entrySet()) {
            selectQuery.setParameter(param.getKey(), param.getValue());
            countQuery.setParameter(param.getKey(), param.getValue());
        }

        List<CommentDTO> list = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResultDTO(list, totalCount);
    }
}
