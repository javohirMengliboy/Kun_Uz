package com.example.controller;

import com.example.dto.ProfileDTO;
import com.example.dto.ProfileFilterDTO;
import com.example.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    /**
     * 1
     */
    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    /**
     * 2
     */
    @PutMapping(value = "/update_status_by_id")
    public ResponseEntity<Boolean> updateStatusById(@RequestBody ProfileDTO dto,
                                                    @RequestParam("id") Integer id) {
        return ResponseEntity.ok(profileService.updateStatusById(dto, id));
    }


    /**
     * 4
     */
    @GetMapping(value = "/get_all")
    public ResponseEntity<List<ProfileDTO>> getAll() {
        return ResponseEntity.ok(profileService.getAll());
    }

    /** 5 . Delete */
    @DeleteMapping(value = "/delete_by_id")
    public ResponseEntity<Boolean> deleteById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(profileService.deleteProfileById(id));
    }

    /** 7. Filter */
    @PostMapping(value = "/filter")
    public ResponseEntity<?> filter(@RequestBody ProfileFilterDTO dto,
                                          @RequestParam("page") Integer page,
                                          @RequestParam("size") Integer size) {
        return ResponseEntity.ok(profileService.filter(dto, page-1,size));
    }

}