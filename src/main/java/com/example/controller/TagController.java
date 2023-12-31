package com.example.controller;

import com.example.dto.TagDTO;
import com.example.enums.ProfileRole;
import com.example.service.TagService;
import com.example.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    @Autowired
    private TagService tagService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(value = "")
    public ResponseEntity<TagDTO> create(@RequestBody TagDTO dto) {
        return ResponseEntity.ok(tagService.create(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Boolean> updateOrderById(@RequestBody TagDTO dto,
                                                   @PathVariable("id") Integer id) {
        return ResponseEntity.ok(tagService.update(dto, id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(tagService.deleteTagById(id));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(value = "")
    public ResponseEntity<List<TagDTO>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }
}
