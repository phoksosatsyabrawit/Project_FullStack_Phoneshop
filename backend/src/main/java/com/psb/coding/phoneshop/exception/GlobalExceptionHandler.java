package com.psb.coding.phoneshop.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler{ // This class is handled all exception that was defined

	@ExceptionHandler(value = {ApiException.class})
	public ResponseEntity<?> handleApiException(ApiException e){
		ErrorResponse errorResponse = new ErrorResponse(e.getStatus(), e.getMessage());
		return ResponseEntity
				.status(e.getStatus())
				.body(errorResponse);
	}
	
	@ExceptionHandler(value = {MethodArgumentNotValidException.class})
	public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e){
		Map<String,String> importHandler = new HashMap<>();
		e.getBindingResult().getAllErrors().forEach(error -> {
			importHandler.put(((FieldError)error).getField(), error.getDefaultMessage());
		});
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(importHandler);
	}
	
	@ExceptionHandler(value = {ConstraintViolationException.class})
	public ResponseEntity<?> handleConstraintsViolationException(ConstraintViolationException e){
		Map<String, String> priceHandler = new HashMap<>();
		e.getConstraintViolations().forEach(error -> {
			priceHandler.put(error.getPropertyPath().toString(), error.getMessage());
		});
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.body(priceHandler);
	}
}
