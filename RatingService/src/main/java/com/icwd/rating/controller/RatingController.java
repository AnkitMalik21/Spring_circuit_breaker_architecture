package com.icwd.rating.controller;

import com.icwd.rating.entity.Rating;
import com.icwd.rating.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    //create rating
    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
        Rating rate = ratingService.create(rating);
        return ResponseEntity.status(HttpStatus.CREATED).body(rate);
    }

    //getAll Rating
    @GetMapping
    public ResponseEntity<List<Rating>> getAllRatings(){
        List<Rating> list = ratingService.getAllRating();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    //get all rating via userId
    @GetMapping("/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(String userId){
        List<Rating> list = ratingService.getRatingByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }
    //get All rating via hotelId
    @GetMapping("/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(String hotelId){
        List<Rating> list = ratingService.getRatingByHotelId(hotelId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

}
