package com.user.exceptions;

import javax.validation.ValidationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.webjars.NotFoundException;

import com.user.pojo.response.GenericResponse;
import com.user.utils.Messages;

public class ProcessExctions {
	public static ResponseEntity<GenericResponse> generatErrorResponse(Exception exception) {
		if (exception instanceof NotFoundException)
			return new ResponseEntity<>(new GenericResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
		if (exception instanceof ValidationException)
			return new ResponseEntity<>(new GenericResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
		if (exception instanceof DataIntegrityViolationException
				&& exception.getMessage().toUpperCase().contains("USER_EMAIL_UNIQUE_CONSTRAINTS"))
			return new ResponseEntity<>(new GenericResponse(Messages.ERROR_USER_UNIQUE_EMAIL), HttpStatus.CONFLICT);
		if (exception instanceof UnauthorizedException)
			return new ResponseEntity<>(new GenericResponse(exception.getMessage()), HttpStatus.UNAUTHORIZED);
		if (exception instanceof ForbiddenException)
			return new ResponseEntity<>(new GenericResponse(exception.getMessage()), HttpStatus.FORBIDDEN);
		return new ResponseEntity<>(new GenericResponse(exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
