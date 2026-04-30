package com.busbooking.exception;

public class SeatNotAvailableException extends RuntimeException {

	public SeatNotAvailableException(String message) {
		super(message);
	}
}
