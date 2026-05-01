package com.busbooking.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	public ResponseEntity<ErrorMessageDto> handleBadRequest(com.busbooking.exception.BadRequestException ex) {
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
	
	 @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<Map<String, String>> handleValidationException(
	            MethodArgumentNotValidException ex) {

	        Map<String, String> errors = new HashMap<>();

	        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
	            errors.put(error.getField(), error.getDefaultMessage());
	        }

	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }
	 
	 @ExceptionHandler(NotAvailableException.class)
	 public ResponseEntity<Map<String, String>> handleNotAvailableException(NotAvailableException ex) {
	     Map<String, String> error = new HashMap<>();
	     error.put("message", ex.getMessage());

	     return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	 }
}
