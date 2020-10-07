package com.systango.shopping.handler;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.systango.shopping.apiresponse.ExceptionResponse;

@ControllerAdvice
public class ApplicationExceptionHandlingAdvice extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Malformed JSON request";
		return buildResponseEntity(new ExceptionResponse(error), HttpStatus.BAD_REQUEST);
	}

	// for @Valid failure
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
    	Optional<ObjectError> error = ex.getBindingResult().getAllErrors()
				.stream()
				.findFirst();
    	String errorMessage = error.get().getDefaultMessage();
    	ExceptionResponse responseBody = new ExceptionResponse(errorMessage);
        return buildResponseEntity(responseBody,status);
	}
	
	// create response entity
	private ResponseEntity<Object> buildResponseEntity(ExceptionResponse apiExceptionResponse, HttpStatus status) {
		return new ResponseEntity<>(apiExceptionResponse, status);
	}
}
