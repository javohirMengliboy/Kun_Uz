package com.example.controller;

import com.example.exp.AppBadRequestException;
import com.example.exp.AppMethodNotAllowedException;
import com.example.exp.ItemNotFoundException;
import com.example.exp.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
//    @ExceptionHandler({ItemNotFoundException.class, AppBadRequestException.class})
//    public ResponseEntity<String> handler(RuntimeException e){
//        return ResponseEntity.badRequest().body(e.getMessage());
//    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<String> handler(UnAuthorizedException e){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(AppMethodNotAllowedException.class)
    public ResponseEntity<String> handler(AppMethodNotAllowedException e){
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handler(RuntimeException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
