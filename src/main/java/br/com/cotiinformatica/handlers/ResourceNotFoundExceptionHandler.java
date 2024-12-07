package br.com.cotiinformatica.handlers;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler {

	@ExceptionHandler(OpenApiResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public List<String> errorHandler(OpenApiResourceNotFoundException e) {

		// Cria uma lista de erros com a mensagem da exceção
		List<String> errors = new ArrayList<>();
		errors.add(e.getMessage());

		return errors;
	}
}
