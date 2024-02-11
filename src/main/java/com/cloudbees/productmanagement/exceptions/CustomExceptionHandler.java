package com.cloudbees.productmanagement.exceptions;

import com.cloudbees.productmanagement.response.ProductResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({ProductException.class})
    public ResponseEntity<ProductResponse> handleProductException(ProductException ex) {
        ProductResponse bookResponse = ProductResponse.builder().success(false).data(ex.getMessage()).build();
        return ResponseEntity.status(ex.getHttpStatusCode()).body(bookResponse);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Map<String,String>> handleNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> errorMap = new HashMap<>();
        ex.getFieldErrors().stream().forEach(fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        return ResponseEntity.status(ex.getStatusCode()).body(errorMap);
    }
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ProductResponse> handleNotReadableException(HttpMessageNotReadableException ex){
        ProductResponse bookResponse = ProductResponse.builder().success(false).data(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bookResponse);
    }
}
