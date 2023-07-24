package com.example.controller;

import com.example.dto.ArticleDTO;
import com.example.dto.JwtDTO;
import com.example.enums.ProfileRole;
import com.example.mapper.ArticleGetMapper;
import com.example.mapper.ArticleMapper;
import com.example.service.ArticleService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping(value = "/admin")
    public ResponseEntity<?> create(@RequestBody ArticleDTO dto,
                                    HttpServletRequest request){
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        System.out.println("control "+jwtDTO.getId()+" "+ jwtDTO.getRole());
        return ResponseEntity.ok(articleService.create(dto, jwtDTO.getId()));
    }


    @PutMapping(value = "/admin/{id}")
    public ResponseEntity<Boolean> update(@RequestBody ArticleDTO dto,
                                                  @PathVariable("id") String id,
                                          HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(dto, id));
    }



    @GetMapping(value = "")
    public ResponseEntity<List<ArticleDTO>> getAll() {
        return ResponseEntity.ok(articleService.getAll());
    }

    @DeleteMapping(value = "/admin/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id,
                                              HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.deleteProfileById(id));
    }

    @GetMapping(value = "/last_5_by_type")
    public ResponseEntity<List<ArticleDTO>> getByType(@RequestParam("type") Integer typeId,
                                                      @RequestParam("limit") Integer limit) {
        return ResponseEntity.ok(articleService.getByType(typeId, limit));
    }

    @PostMapping(value = "/last_8")
    public ResponseEntity<List<ArticleDTO>> getIDNotIncluded(@RequestBody() List<String> idList) {
        return ResponseEntity.ok(articleService.getIDNotIncluded(idList));
    }

    @GetMapping(value = "/get_by_id/{id}")
    public ResponseEntity<ArticleDTO> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(articleService.getById(id));
    }

    @GetMapping(value = "/get_by_type_not_id")
    public ResponseEntity<List<ArticleGetMapper>> getByTypeNotId(@RequestParam("type") Integer type,
                                                                 @RequestParam("id") String id) {
        return ResponseEntity.ok(articleService.getByTypeNotId(id, type));
    }
}
