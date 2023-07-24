package com.example.controller;

import com.example.dto.RegionDTO;
import com.example.enums.ProfileRole;
import com.example.mapper.LanguageMapper;
import com.example.service.RegionService;
import com.example.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    @PostMapping(value = "/admin")
    public ResponseEntity<?> create(@RequestBody RegionDTO dto,
                                    @RequestHeader("Authorization") String authToken) {
        SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.create(dto));
    }

    @PutMapping(value = "/admin/{id}")
    public ResponseEntity<Boolean> updateOrderById(@RequestBody RegionDTO dto,
                                                   @PathVariable("id") Integer id,
                                                   @RequestHeader("Authorization") String authToken) {
        SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.update(dto, id));
    }
    @DeleteMapping(value = "/admin/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id,
                                              @RequestHeader("Authorization") String authToken) {
        SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.deleteRegionById(id));
    }

    @GetMapping(value = "/admin")
    public ResponseEntity<List<RegionDTO>> getAll(@RequestHeader("Authorization") String authToken) {
        SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping(value = "/by_lang")
    public ResponseEntity<List<LanguageMapper>> getByLanguage(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(regionService.getByLanguage(lang));
    }
}
