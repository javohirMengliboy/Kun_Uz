package com.example.controller;

import com.example.dto.RegionDTO;
import com.example.enums.ProfileRole;
import com.example.mapper.LanguageMapper;
import com.example.service.RegionService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/region")
public class RegionController {
    private RegionService regionService;
    @Autowired
    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    @PostMapping(value = "")
    public ResponseEntity<RegionDTO> create(@RequestBody RegionDTO dto,
                                    HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.create(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Boolean> updateOrderById(@RequestBody RegionDTO dto,
                                                   @PathVariable("id") Integer id,
                                                   HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.update(dto, id));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id,
                                              HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.deleteRegionById(id));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<RegionDTO>> getAll(HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(regionService.getAll());
    }

    @GetMapping(value = "/by_lang")
    public ResponseEntity<List<LanguageMapper>> getByLanguage(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(regionService.getByLanguage(lang));
    }
}
