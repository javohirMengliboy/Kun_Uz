package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.AuthDTO;
import com.example.dto.RegistrationDTO;
import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.service.AuthService;
import com.example.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @PostMapping(value = {"/login"})
    public ResponseEntity<ApiResponseDTO> login(@RequestBody AuthDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(value = {"/registration"})
    public ResponseEntity<ApiResponseDTO> registration(@RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }


    @GetMapping(value = {"/verification/email/{jwt}"})
    public ResponseEntity<ApiResponseDTO> verificationEmail(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }
}
