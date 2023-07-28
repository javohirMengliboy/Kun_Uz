package com.example.controller;

import com.example.dto.AttachDTO;
import com.example.enums.ProfileRole;
import com.example.service.AttachService;
import com.example.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(attachService.save(file));
    }

    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        return this.attachService.loadImage(fileName);
    }


    @GetMapping(value = "/open/{id}/img", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] openImageById(@PathVariable("id") String id) {
        return attachService.loadImageById(id);
    }

    @GetMapping(value = "/open/{id}/general", produces = MediaType.ALL_VALUE)
    public byte[] openByIdGeneral(@PathVariable("id") String id) {
        return attachService.loadByIdGeneral(id);
    }

    @GetMapping(value = "/admin/pagination")
    public ResponseEntity<List<AttachDTO>> pagination(@RequestParam("page") int page,
                                           @RequestParam("size") int size,
                                           HttpServletRequest request) {
        SecurityUtil.hasRole(request, ProfileRole.MODERATOR);
        return ResponseEntity.ok().body(attachService.pagination(page,size));
    }
    @DeleteMapping(value = "/admin/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") String id,
                                          HttpServletRequest request) {
        System.out.println(request.getAttribute("role"));
        SecurityUtil.hasRole(request, ProfileRole.ADMIN);
        return ResponseEntity.ok().body(attachService.delete(id));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") String id) {
        return attachService.download(id);
    }

}
