package com.busbooking.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public class ReviewRequestDto {

	@Min(1)
    private Long customerId;
	@Min(1)
    private Long tripId;

    @Min( value=1  , message = "Rating must be greater than 1")
    @Max( value=5 ,message = "Rating must be less than equal to 5")
    
    private Integer rating;
    
    @Pattern(
    	    regexp = "^[a-zA-Z0-9 .,!?'-]{3,200}$",
    	    message = "Comment must be 3-200 characters and contain only simple alpahabets and numbers"
    	)
    private String comment;

   

    public ReviewRequestDto() {}

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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