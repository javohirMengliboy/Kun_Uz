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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/profile")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @PostMapping(value = "")
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<Boolean> update(@RequestBody ProfileDTO dto,
                                          @PathVariable("id") Integer id) {
        return ResponseEntity.ok(profileService.update(dto, id));
    }

    @PutMapping(value = "/detail")
    public ResponseEntity<Boolean> updateDetail(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.updateDetail(dto));
    }



    @GetMapping(value = "")
    public ResponseEntity<List<ProfileDTO>> getAll() {
        return ResponseEntity.ok(profileService.getAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(profileService.deleteProfileById(id));
    }

    @PutMapping(value = "/update_img")
    public ResponseEntity<String> updateImage(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.updateImage(dto));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<PageImpl<ProfileDTO>> filter(@RequestBody ProfileFilterDTO filterDTO,
                                                       @RequestParam("page") Integer page,
                                                       @RequestParam("size") Integer size) {
        return ResponseEntity.ok(profileService.filter(filterDTO, page-1,size));
    }

}