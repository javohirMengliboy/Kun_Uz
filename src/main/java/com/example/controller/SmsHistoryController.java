package com.example.controller;

import com.example.dto.EmailHistoryDTO;
import com.example.dto.SmsHistoryDTO;
import com.example.enums.ProfileRole;
import com.example.service.SmsHistoryService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/smsHistory")
public class SmsHistoryController {
    @Autowired
    private SmsHistoryService smsHistoryService;
    @GetMapping(value = "/get/by_phone")
    public ResponseEntity<SmsHistoryDTO> getByPhone(@RequestParam("phone") String phone,
                                                    HttpServletRequest request){
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(smsHistoryService.getByPhone(phone));
    }

    @GetMapping(value = "/get/by_createdDate")
    public ResponseEntity<List<SmsHistoryDTO>> getByCreatedDate(@RequestParam("createdDate") LocalDate date,
                                                                  HttpServletRequest request){
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(smsHistoryService.getByCreatedDate(date));
    }

    @GetMapping(value = "/get/pagination")
    public ResponseEntity<PageImpl<SmsHistoryDTO>> getPagination(@RequestParam("page") int page,
                                                                   @RequestParam("size") int size,
                                                                   HttpServletRequest request){
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(smsHistoryService.getPagination(page, size));
    }

}
