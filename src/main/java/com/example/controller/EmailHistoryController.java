package com.example.controller;

import com.example.dto.EmailHistoryDTO;
import com.example.enums.ProfileRole;
import com.example.service.EmailHistoryService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/emailHistory")
public class EmailHistoryController {
    @Autowired
    private EmailHistoryService emailHistoryService;
    @GetMapping(value = "/get/by_email")
    public ResponseEntity<List<EmailHistoryDTO>> getByEmail(@RequestParam("email") String email){
        return ResponseEntity.ok(emailHistoryService.getByEmail(email));
    }

    @GetMapping(value = "/get/by_createdDate")
    public ResponseEntity<List<EmailHistoryDTO>> getByCreatedDate(@RequestParam("createdDate") LocalDate date){
        return ResponseEntity.ok(emailHistoryService.getByCreatedDate(date));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/pagination")
    public ResponseEntity<PageImpl<EmailHistoryDTO>> getPagination(@RequestParam("page") int page,
                                                                   @RequestParam("size") int size){
        return ResponseEntity.ok(emailHistoryService.getPagination(page, size));
    }
}
