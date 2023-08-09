package com.example.controller;

import com.example.dto.SavedArticleDTO;
import com.example.mapper.ArticleGetMapper;
import com.example.service.SavedArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/savedArticle")
public class SavedArticleController {
    @Autowired
    private SavedArticleService savedArticleService;

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR', 'ROLE_PUBLISHER')")
    @PostMapping(value = "")
    public ResponseEntity<SavedArticleDTO> create(@RequestBody() SavedArticleDTO dto){
        return ResponseEntity.ok().body(savedArticleService.create(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR', 'ROLE_PUBLISHER')")
    @DeleteMapping(value = "")
    public ResponseEntity<Boolean> delete(@RequestParam("articleId") String articleId){
        return ResponseEntity.ok().body(savedArticleService.delete(articleId));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR', 'ROLE_PUBLISHER')")
    @GetMapping(value = "")
    public ResponseEntity<List<ArticleGetMapper>> getAllList(){
        return ResponseEntity.ok().body(savedArticleService.getList());
    }

}
