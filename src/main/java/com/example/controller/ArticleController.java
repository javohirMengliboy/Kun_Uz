package com.example.controller;

import com.example.dto.*;
import com.example.enums.ProfileRole;
import com.example.mapper.ArticleGetMapper;
import com.example.mapper.ArticleMapper;
import com.example.service.ArticleService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    //  1. CREATE (Moderator) status(NotPublished)
    @PostMapping(value = "/role")
    public ResponseEntity<?> create(@RequestBody ArticleDTO dto,
                                    HttpServletRequest request){
        JwtDTO jwtDTO = SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.create(dto, jwtDTO.getId()));
    }

    //  2. Update (Moderator (status to not publish)) (remove old image)
    @PutMapping(value = "/role/{id}")
    public ResponseEntity<Boolean> update(@RequestBody ArticleDTO dto,
                                                  @PathVariable("id") String id,
                                          HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok(articleService.update(dto, id));
    }

    //  3. Delete Article (MODERATOR)
    @DeleteMapping(value = "/role/{id}")
    public ResponseEntity<Boolean> deleteById(@PathVariable("id") String id,
                                              HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok(articleService.deleteProfileById(id));
    }

    //  5. Get Last 5 Article By Types  ordered_by_created_date
    @GetMapping(value = "/get/last_5_by_type")
    public ResponseEntity<List<ArticleDTO>> getByType(@RequestParam("type") Integer typeId,
                                                      @RequestParam("limit") Integer limit) {
        return ResponseEntity.ok(articleService.getByType(typeId, limit));
    }

    //   7. Get Last 8  Articles witch id not included in given list.
    @PostMapping(value = "/get/last_8")
    public ResponseEntity<List<ArticleDTO>> getIDNotIncluded(@RequestBody() List<String> idList) {
        return ResponseEntity.ok(articleService.getIDNotIncluded(idList));
    }

    //   8. Get Article By Id And Lang
    @GetMapping(value = "/get/by_id/{id}")
    public ResponseEntity<ArticleDTO> getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(articleService.getById(id));
    }

    //  9. Get Last 4 Article By Types and except given article id.
    @GetMapping(value = "/get/by_type_not_id")
    public ResponseEntity<List<ArticleGetMapper>> getByTypeNotId(@RequestParam("type") Integer type,
                                                                 @RequestParam("id") String id) {
        return ResponseEntity.ok(articleService.getByTypeNotId(id, type));
    }

    //  10. Get 4 most read articles
    @GetMapping(value = "/get/four_most_read")
    public ResponseEntity<List<ArticleGetMapper>> getFourMostRead() {
        return ResponseEntity.ok(articleService.getFourMostRead());
    }

    //  11. Get Last 4 Article By TagName
    @GetMapping(value = "/get/last_four_by_tag")
    public ResponseEntity<List<ArticleGetMapper>> getLasFourByTag(@RequestParam("tagId") int tagId) {
        return ResponseEntity.ok(articleService.getLasFourByTag(tagId));
    }

    //  12. Get Last 5 Article By Types  And By Region KeyU
    @GetMapping(value = "/get/last_five_by_type_and_region")
    public ResponseEntity<List<ArticleGetMapper>> getByTypeAndRegion(@RequestParam("typeId") Integer typeId,
                                                                     @RequestParam("regionId") Integer regionId) {
        return ResponseEntity.ok(articleService.getByTypeAndRegion(typeId, regionId));
    }

    //   13. Get Article list by Region Key (Pagination)
    @GetMapping(value = "/get/pagination_by_region")
    public ResponseEntity<List<ArticleGetMapper>> getPaginationByRegion(@RequestParam("regionId") Integer regionId,
                                                                        @RequestParam("page") int page,
                                                                        @RequestParam("size") int size) {
        return ResponseEntity.ok(articleService.getPaginationByRegion(regionId, page, size));
    }

    //   14. Get Last 5 Article Category Key
    @GetMapping(value = "/get/last_five_by_category")
    public ResponseEntity<List<ArticleGetMapper>> getLastFiveByCategory(@RequestParam("categoryId") Integer categoryId) {
        return ResponseEntity.ok(articleService.getLastFiveByCategory(categoryId));
    }

    //   15. Get Article By Category Key (Pagination)
    @GetMapping(value = "/get/pagination_by_category")
    public ResponseEntity<List<ArticleGetMapper>> getPaginationByCategory(@RequestParam("categoryId") Integer categoryId,
                                                                        @RequestParam("page") int page,
                                                                        @RequestParam("size") int size) {
        return ResponseEntity.ok(articleService.getPaginationByCategory(categoryId, page, size));
    }

    //   16. Increase Article View Count by ArticleId
    @GetMapping(value = "/get/increase_view_count")
    public ResponseEntity<Boolean> increaseViewCount(@PathVariable("id") String id) {
        return ResponseEntity.ok(articleService.increaseViewCount(id));
    }

    //   17. Increase Share View Count by ArticleId
    @GetMapping(value = "/get/increase_share_count")
    public ResponseEntity<Boolean> increaseShareCount(@PathVariable("id") String id) {
        return ResponseEntity.ok(articleService.increaseShareCount(id));
    }

    //  18. Filter Article
    @PostMapping(value = "/filter")
    public ResponseEntity<PageImpl<ArticleFilterDTO>> filter(@RequestParam(value = "page",defaultValue = "1") int page,
                                                             @RequestParam(value = "size",defaultValue = "10") int size,
                                                             @RequestBody ArticleFilterDTO filterDTO,
                                                             HttpServletRequest request){
        SecurityUtil.hasRole(request, null);
        return ResponseEntity.ok().body(articleService.filter(filterDTO, page, size));
    }

    @GetMapping(value = "/get")
    public ResponseEntity<List<ArticleDTO>> getAll() {
        return ResponseEntity.ok(articleService.getAll());
    }

}
