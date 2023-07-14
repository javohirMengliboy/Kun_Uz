package com.example.repository;

import com.example.dto.FilterResultDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class ProfileCustomRepository {
    @Autowired
    private EntityManager entityManager;

    public FilterResultDTO filter(ProfileFilterDTO dto, Integer page, Integer size){
        StringBuilder selectBuilder = new StringBuilder();
        selectBuilder.append("select new com.example.dto.ProfileDTO" +
                "(id, name, surname, email, phone, password, status, role, visible, createdDate, photoId)" +
                " from ProfileEntity  where 1 = 1");
        StringBuilder countBuilder = new StringBuilder("select count(p) from ProfileEntity p where 1 = 1");

        Map<String, Object> params = new HashMap<>();

        StringBuilder stringBuilder = new StringBuilder();
        if (dto.getName() != null){
            stringBuilder.append(" and name = :name");
            params.put("name", dto.getName());
        }
        if (dto.getSurname() != null){
            stringBuilder.append(" and surname = :surname");
            params.put("surname", dto.getSurname());
        }
        if (dto.getPhone() != null){
            stringBuilder.append(" and phone = :phone");
            params.put("phone", dto.getPhone());
        }
        if (dto.getRole() != null){
            stringBuilder.append(" and role = :role");
            params.put("role", dto.getRole());
        }
        if (dto.getDateFrom() != null && dto.getDateTo() != null){
            stringBuilder.append(" and createdDate between :from and :to");
            params.put("from", LocalDateTime.of(dto.getDateFrom(), LocalTime.MIN));
            params.put("to", LocalDateTime.of(dto.getDateTo(), LocalTime.MAX));
        }else if (dto.getDateFrom() != null){
            stringBuilder.append(" and createdDate >= :from");
            params.put("from", dto.getDateFrom());
        }else if (dto.getDateTo() != null){
            stringBuilder.append(" and createdDate < :to");
            params.put("to", dto.getDateTo());
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

        List<ProfileDTO> list = selectQuery.getResultList();
        Long totalCount = (Long) countQuery.getSingleResult();

        return new FilterResultDTO(list, totalCount);
    }
}
