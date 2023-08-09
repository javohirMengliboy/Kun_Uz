package com.example.controller;

import com.example.dto.ArticleLikeDTO;
import com.example.dto.JwtDTO;
import com.example.service.ArticleLikeService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/article_like")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR', 'ROLE_PUBLISHER')")
    @PostMapping(value = "/like")
    public ResponseEntity<ArticleLikeDTO> like(@RequestBody ArticleLikeDTO dto){
        return ResponseEntity.ok().body(articleLikeService.like(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN','ROLE_MODERATOR', 'ROLE_PUBLISHER')")
    @PostMapping(value = "/dislike")
    public ResponseEntity<ArticleLikeDTO> dislike(@RequestBody ArticleLikeDTO dto){
        return ResponseEntity.ok().body(articleLikeService.dislike(dto));
    }

}
