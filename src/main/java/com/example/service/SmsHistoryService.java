package com.example.service;

import com.example.dto.SmsHistoryDTO;
import com.example.entity.SmsHistoryEntity;
import com.example.exp.ItemNotFoundException;
import com.example.repository.SmsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SmsHistoryService {
    @Autowired
    private SmsHistoryRepository smsHistoryRepository;

    public void create(SmsHistoryEntity entity){
        smsHistoryRepository.save(entity);
    }

    public SmsHistoryDTO getByPhone(String email) {
        return smsHistoryRepository.findByPhone(email).map(this::toDTO).orElseThrow(()-> new ItemNotFoundException("EmailHistory not found"));
    }

    public List<SmsHistoryDTO> getByCreatedDate(LocalDate date) {
        LocalDateTime from = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(date, LocalTime.MAX);
        List<SmsHistoryEntity> entityList = smsHistoryRepository.findByCreatedDateBetween(from, to);
        if (entityList.isEmpty()) {
            throw new ItemNotFoundException("EmailHistory not found");
        }
        return entityList.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public PageImpl<SmsHistoryDTO> getPagination(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC,"createdDate"));
        Page<SmsHistoryEntity> pageObj = smsHistoryRepository.findAllBy(pageable);
        List<SmsHistoryDTO> dtoList = pageObj.stream().map(this::toDTO).collect(Collectors.toList());
        return new PageImpl<>(dtoList, pageable,pageObj.getTotalElements());
    }

    private SmsHistoryDTO toDTO(SmsHistoryEntity entity) {
        SmsHistoryDTO dto = new SmsHistoryDTO();
        dto.setId(entity.getId());
        dto.setPhone(entity.getPhone());
        dto.setMessage(entity.getMessage());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

}
