package com.example.controller;

import com.example.dto.ArticleTypeDTO;
import com.example.enums.ProfileRole;
import com.example.mapper.LanguageMapper;
import com.example.service.ArticleTypeService;
import com.example.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articleType")
public class ArticleTypeController {
    private ArticleTypeService articleTypeService;
    @Autowired
    public void setArticleTypeService(ArticleTypeService articleTypeService) {
        this.articleTypeService = articleTypeService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping(value = "")
    public ResponseEntity<ArticleTypeDTO> create(@RequestBody ArticleTypeDTO dto) {
        return ResponseEntity.ok(articleTypeService.create(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Boolean> updateOrderById(@RequestBody ArticleTypeDTO dto,
                                                   @PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleTypeService.update(dto, id));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(articleTypeService.deleteArticleTypeById(id));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<ArticleTypeDTO>> getAll() {
        return ResponseEntity.ok(articleTypeService.getAll());
    }

    @GetMapping(value = "/open/by_lang")
    public ResponseEntity<List<LanguageMapper>> getByLanguage(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(articleTypeService.getByLanguage(lang));
    }
}
