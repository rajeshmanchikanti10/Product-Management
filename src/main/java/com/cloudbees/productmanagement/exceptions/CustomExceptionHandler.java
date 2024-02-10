package com.cloudbees.productmanagement.exceptions;

import com.cloudbees.productmanagement.response.ProductResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler({ProductException.class})
    public ResponseEntity<ProductResponse> handleProductException(ProductException ex) {
        ProductResponse bookResponse = ProductResponse.builder().success(false).data(ex.getMessage()).build();
        return ResponseEntity.status(ex.getHttpStatusCode()).body(bookResponse);
    }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getFieldError().getDefaultMessage());
//    }
}
