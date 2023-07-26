package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.enums.ProfileRole;
import com.example.mapper.LanguageMapper;
import com.example.service.CategoryService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
public class CategoryController {
    private CategoryService categoryService;
    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping(value = "")
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO dto,
                                    HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Boolean> updateOrderById(@RequestBody CategoryDTO dto,
                                                   @PathVariable("id") Integer id,
                                                   @RequestHeader("Authorization") String authToken) {
        SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.update(dto, id));
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") Integer id,
                                              @RequestHeader("Authorization") String authToken) {
        SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.deleteCategoryById(id));
    }

    @GetMapping(value = "")
    public ResponseEntity<List<CategoryDTO>> getAll(@RequestHeader("Authorization") String authToken) {
        SecurityUtil.hasRole(authToken, ProfileRole.ADMIN);
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping(value = "/by_lang")
    public ResponseEntity<List<LanguageMapper>> getByLanguage(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(categoryService.getByLanguage(lang));
    }

}
