package com.example.controller;

import com.example.dto.CategoryDTO;
import com.example.dto.ProfileDTO;
import com.example.mapper.CategoryLanguageMapper;
import com.example.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping(value = "/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }

    @PutMapping(value = "/update_order_by_id")
    public ResponseEntity<Boolean> updateOrderById(@RequestBody CategoryDTO dto,
                                                    @RequestParam("id") Integer id) {
        return ResponseEntity.ok(categoryService.updateOrderById(dto, id));
    }
    @DeleteMapping(value = "/delete_by_id")
    public ResponseEntity<Boolean> deleteById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(categoryService.deleteProfileById(id));
    }

    @GetMapping(value = "/get_all")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        return ResponseEntity.ok(categoryService.getAll());
    }

    @GetMapping(value = "/get_by_lang")
    public ResponseEntity<List<CategoryLanguageMapper>> getByLanguage(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(categoryService.getByLanguage(lang));
    }

}
