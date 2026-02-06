package com.hbk.corefw.advice;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.dto.DTOWrapper;
import com.hbk.corefw.exception.ResourceNotFoundException;
import com.hbk.corefw.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CoreControllerAdvice {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<DTOWrapper<CoreDTO>> handleValidationException(ValidationException e) {
        return ResponseEntity.badRequest().body(new DTOWrapper<>(e.getErrors()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DTOWrapper<CoreDTO>> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DTOWrapper<>(e.getErrors()));
    }

}
