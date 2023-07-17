package com.example.controller;

import com.example.exp.AppBadRequestException;
import com.example.exp.ItemNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({ItemNotFoundException.class, AppBadRequestException.class})
    public ResponseEntity<String> handler(RuntimeException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
