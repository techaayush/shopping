package com.systango.shopping.apiresponse;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;

	private String message;
	
	private ExceptionResponse() {
		timestamp = LocalDateTime.now();
	}

	public ExceptionResponse(String message) {
		this();
		this.message = message;
	}

	public ExceptionResponse(Exception ex) {
		this();
		this.message = ex.getMessage();
	}
	
}
