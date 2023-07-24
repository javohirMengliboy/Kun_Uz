package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping(value = "")
    public ResponseEntity<?> create(@RequestBody ProfileDTO dto,
                                    HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto, jwtDTO.getId()));
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<Boolean> updateForAdmin(@RequestBody ProfileDTO dto,
                                                  @PathVariable("id") Integer id,
                                                  @RequestHeader("Authorization") String authToken) {
        SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.updateForAdmin(dto, id));
    }

    @PutMapping(value = "/for_user/")
    public ResponseEntity<Boolean> updateForUser(@RequestBody ProfileDTO dto,
                                                 @PathVariable("id") Integer id,
                                                 @RequestHeader("Authorization") String authToken) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(authToken, null);
        return ResponseEntity.ok(profileService.updateForUser(jwtDTO.getId(), dto));
    }



    @GetMapping(value = "")
    public ResponseEntity<List<ProfileDTO>> getAll(@RequestHeader("Authorization") String authToken) {
        SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.getAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id,
                                              @RequestHeader("Authorization") String authToken) {
        SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.deleteProfileById(id));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileFilterDTO dto,
                                    @RequestParam("page") Integer page,
                                    @RequestParam("size") Integer size,
                                    @RequestHeader("Authorization") String authToken) {
        SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.filter(dto, page-1,size));
    }

}