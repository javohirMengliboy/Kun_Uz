package com.example.controller;

import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.service.ProfileService;
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
    public ResponseEntity<?> create(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }


    @PutMapping(value = "/{id}")
    public ResponseEntity<Boolean> updateForAdmin(@RequestBody ProfileDTO dto,
                                                  @PathVariable("id") Integer id) {
        return ResponseEntity.ok(profileService.updateForAdmin(dto, id));
    }

    @PutMapping(value = "/for_user/{id}")
    public ResponseEntity<Boolean> updateForUser(@RequestBody ProfileDTO dto,
                                                  @PathVariable("id") Integer id) {
        return ResponseEntity.ok(profileService.updateForUser(dto, id));
    }



    @GetMapping(value = "")
    public ResponseEntity<List<ProfileDTO>> getAll() {
        return ResponseEntity.ok(profileService.getAll());
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(profileService.deleteProfileById(id));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileFilterDTO dto,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size) {
        return ResponseEntity.ok(profileService.filter(dto, page-1,size));
    }

}