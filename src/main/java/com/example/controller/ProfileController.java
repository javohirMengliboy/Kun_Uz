package com.example.controller;

import com.example.dto.JwtDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.enums.ProfileRole;
import com.example.service.ProfileService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping(value = "")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto,
                                             HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.create(dto, jwtDTO.getId()));
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<Boolean> update(@RequestBody ProfileDTO dto,
                                          @PathVariable("id") Integer id,
                                          HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.update(dto, id));
    }

    @PutMapping(value = "/detail")
    public ResponseEntity<Boolean> updateDetail(@RequestBody ProfileDTO dto,
                                                HttpServletRequest request) {
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, null);
        return ResponseEntity.ok(profileService.updateDetail(jwtDTO.getId(), dto));
    }



    @GetMapping(value = "")
    public ResponseEntity<List<ProfileDTO>> getAll(HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.getAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id,
                                              HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.deleteProfileById(id));
    }

    @PutMapping(value = "/update_img")
    public ResponseEntity<String> updateImage(@RequestBody ProfileDTO dto,
                                              HttpServletRequest request) {
        System.out.println(dto.getImageId()+" "+ request.getAttribute("role"));
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, null);
        return ResponseEntity.ok(profileService.updateImage(jwtDTO.getId(), dto));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestBody ProfileFilterDTO filterDTO,
                                                       @RequestParam("page") Integer page,
                                                       @RequestParam("size") Integer size,
                                                       HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(profileService.filter(filterDTO, page-1,size));
    }

}