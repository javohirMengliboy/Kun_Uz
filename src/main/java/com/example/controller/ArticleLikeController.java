package com.example.controller;

import com.example.dto.ArticleLikeDTO;
import com.example.dto.JwtDTO;
import com.example.service.ArticleLikeService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/article_like")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping(value = "/like")
    public ResponseEntity<ArticleLikeDTO> like(@RequestBody ArticleLikeDTO dto,
                                               HttpServletRequest request){
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, null);
        return ResponseEntity.ok().body(articleLikeService.like(jwtDTO.getId(), dto));
    }

    @PostMapping(value = "/dislike")
    public ResponseEntity<ArticleLikeDTO> dislike(@RequestBody ArticleLikeDTO dto,
                                                  HttpServletRequest request){
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, null);
        return ResponseEntity.ok().body(articleLikeService.dislike(jwtDTO.getId(), dto));
    }

}
