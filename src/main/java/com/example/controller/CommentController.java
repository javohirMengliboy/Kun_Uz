package com.example.controller;

import com.example.dto.CommentDTO;
import com.example.dto.CommentFilterDTO;
import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.service.CommentService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {
    private CommentService commentService;
    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping(value = "")
    public ResponseEntity<CommentDTO> create(@RequestBody CommentDTO dto){
        return ResponseEntity.ok().body(commentService.create(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CommentDTO> update(@RequestBody CommentDTO dto,
                                             @PathVariable("id") String commentId){
        return ResponseEntity.ok().body(commentService.update(dto,commentId));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String commentId){
        return ResponseEntity.ok().body(commentService.delete(commentId));
    }

    @GetMapping(value = "/by_article")
    public ResponseEntity<List<CommentDTO>> getByArticle(@RequestParam("articleId") String articleId){
        return ResponseEntity.ok().body(commentService.getByArticle(articleId));
    }

    @GetMapping(value = "/pagination")
    public ResponseEntity<List<CommentDTO>> pagination(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                       @RequestParam(value = "size",defaultValue = "10") Integer size){
        return ResponseEntity.ok().body(commentService.pagination(page,size));
    }

    @PostMapping(value = "/filter")
    public ResponseEntity<PageImpl<CommentDTO>> getByArticle(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                             @RequestParam(value = "size",defaultValue = "10") Integer size,
                                                             @RequestBody CommentFilterDTO filterDTO){
        return ResponseEntity.ok().body(commentService.filter(filterDTO, page, size));
    }
    @GetMapping(value = "/by_replay")
    public ResponseEntity<List<CommentDTO>> getByReplay(@RequestParam("replayId") Integer replayId){
        return ResponseEntity.ok().body(commentService.getByReplay(replayId));
    }
}
