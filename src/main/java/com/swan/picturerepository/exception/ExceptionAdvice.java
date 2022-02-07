package com.swan.picturerepository.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ExceptionAdvice {
	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex, Model model) {
		model.addAttribute("exception", ex);
		return ".error/board/error";
	}
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handlePageNotFound(NoHandlerFoundException ex) {
		return ".error/board/error404";
	}
}
