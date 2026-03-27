package com.icwd.rating.service;

import com.icwd.rating.entity.Rating;

import java.util.List;

public interface RatingService {

    //create
    Rating create(Rating rating);

    //getAll
    List<Rating> getAllRating();

    //get all by userId
    List<Rating> getRatingByUserId(String userId);

    //get all by hotel
    List<Rating> getRatingByHotelId(String hotel);

}
