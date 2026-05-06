package com.busbooking.dto;

public class ReviewDto {
	
	private Long tripId;
	
	private Integer rating;
	
	private String comment;

	public ReviewDto() {
		super();
	}

	public ReviewDto(Long tripId, Integer rating, String comment) {
		super();
		this.tripId = tripId;
		this.rating = rating;
		this.comment = comment;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
