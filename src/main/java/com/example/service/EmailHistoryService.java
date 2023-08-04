package com.example.service;

import com.example.dto.EmailHistoryDTO;
import com.example.entity.EmailHistoryEntity;
import com.example.exp.ItemNotFoundException;
import com.example.repository.EmailHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailHistoryService {
    @Autowired
    private EmailHistoryRepository emailHistoryRepository;

    public void create(EmailHistoryEntity entity){
        emailHistoryRepository.save(entity);
    }

    public List<EmailHistoryDTO> getByEmail(String email) {
        List<EmailHistoryEntity> entityList = emailHistoryRepository.findByEmail(email);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Email History not found");
        }
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<EmailHistoryDTO> getByCreatedDate(LocalDate date) {
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);
        List<EmailHistoryEntity> entityList = emailHistoryRepository.findByCreatedDateBetween(from, to);
        if (entityList.isEmpty()) { 
            throw new ItemNotFoundException("EmailHistory not found");
        }
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PageImpl<EmailHistoryDTO> getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"createdDate"));
        Page<EmailHistoryEntity> pageObj = emailHistoryRepository.findAllBy(pageable);
        List<EmailHistoryDTO> dtoList = pageObj.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable,pageObj.getTotalElements());
    }

    private EmailHistoryDTO toDTO(EmailHistoryEntity entity) {
        EmailHistoryDTO dto = new EmailHistoryDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }
}
