package com.guildify.guildify.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class RestErrorHandler {

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<GuildfyApiExceptionTemplate> handleException(UnauthorizedException e) {
        GuildfyApiExceptionTemplate template = getGuildfyApiExceptionTemplate(e.getMessage(),HttpStatus.UNAUTHORIZED);
        return new ResponseEntity<>(template, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GuildfyApiExceptionTemplate> handleException(IllegalArgumentException e) {
        GuildfyApiExceptionTemplate template = getGuildfyApiExceptionTemplate(e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(template, HttpStatus.BAD_REQUEST);
    }

    private GuildfyApiExceptionTemplate getGuildfyApiExceptionTemplate(String message, HttpStatus status) {
        GuildfyApiExceptionTemplate template = new GuildfyApiExceptionTemplate();
        template.setExceptionMessage(message);
        template.setStatusCode(status.value());
        template.setExceptionDate(LocalDateTime.now());
        return template;
    }
}
