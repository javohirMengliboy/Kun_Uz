package com.example.controller;

import com.example.dto.CommentLikeDTO;
import com.example.dto.JwtDTO;
import com.example.service.CommentLikeService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment_like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping(value = "/like")
    public ResponseEntity<CommentLikeDTO> like(@RequestBody CommentLikeDTO dto){
        return ResponseEntity.ok().body(commentLikeService.like(dto));
    }

    @PostMapping(value = "/dislike")
    public ResponseEntity<CommentLikeDTO> dislike(@RequestBody CommentLikeDTO dto){
        return ResponseEntity.ok().body(commentLikeService.dislike(dto));
    }
}
