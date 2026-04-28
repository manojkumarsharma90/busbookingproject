package com.busbooking.exception;

import java.time.LocalDateTime;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.busbooking.dto.ErrorMessageDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ErrorMessageDto handleBadCredentials(BadCredentialsException ex) {
		ErrorMessageDto dto = new ErrorMessageDto();
		dto.setErrormsg(ex.getMessage());
		dto.setStatus(HttpStatus.UNAUTHORIZED.toString());
		dto.setTimeStamp(LocalDateTime.now());
		return dto;
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessageDto> handleResourceNotFound(ResourceNotFoundException ex) {
		return buildError(ex.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorMessageDto> handleBadRequest(BadRequestException ex) {
		return buildError(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ErrorMessageDto> handleDuplicate(DuplicateResourceException ex) {
		return buildError(ex.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(SeatNotAvailableException.class)
	public ResponseEntity<ErrorMessageDto> handleSeat(SeatNotAvailableException ex) {
		return buildError(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorMessageDto> handleIllegalArg(IllegalArgumentException ex) {
		return buildError(ex.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorMessageDto> handleGeneral(Exception ex) {
		return buildError("An unexpected error occurred: " + ex.getMessage(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ResponseEntity<ErrorMessageDto> buildError(String message, HttpStatus status) {
		ErrorMessageDto err = new ErrorMessageDto();
		err.setErrormsg(message);
		err.setStatus(status.toString());
		err.setTimeStamp(LocalDateTime.now());
		return new ResponseEntity<>(err, status);
	}
}
