package com.spring_boot_template.presentation.handler;

import com.spring_boot_template.domain.exception.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RequiredArgsConstructor
public final class AccessDeniedExceptionHandler {
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> execute(final AccessDeniedException exception) {
        final String message = exception.getMessage();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
    }
}