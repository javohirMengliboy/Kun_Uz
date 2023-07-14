package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.RegionDTO;
import com.example.mapper.CategoryLanguageMapper;
import com.example.mapper.RegionLanguageMapper;
import com.example.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody RegionDTO dto) {
        return ResponseEntity.ok(regionService.create(dto));
    }

    @PutMapping(value = "/update_order_by_id")
    public ResponseEntity<Boolean> updateOrderById(@RequestBody CategoryDTO dto,
                                                   @RequestParam("id") Integer id) {
        return ResponseEntity.ok(regionService.updateOrderById(dto, id));
    }
    @DeleteMapping(value = "/delete_by_id")
    public ResponseEntity<Boolean> deleteById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(regionService.deleteProfileById(id));
    }

    @GetMapping(value = "/get_all")
    public ResponseEntity<List<RegionDTO>> getAll() {
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping(value = "/get_by_lang")
    public ResponseEntity<List<RegionLanguageMapper>> getByLanguage(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(regionService.getByLanguage(lang));
    }
}
