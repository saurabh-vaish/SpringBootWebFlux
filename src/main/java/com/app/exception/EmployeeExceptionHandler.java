package com.app.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeExceptionHandler {
	
	
	@ExceptionHandler(EmployeeNotFoundException.class)
	public String handleEmployeeNotFoundException()
	{
		return "error";
	}

}
