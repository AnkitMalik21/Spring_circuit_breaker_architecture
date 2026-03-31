package com.icwd.user_service.feign;

import com.icwd.user_service.entity.Rating;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="RATINGSERVICE")
public interface RatingService {

    @PostMapping("/api/rating")
    Rating create(@RequestBody Rating rating);

    @GetMapping("/api/rating")
    List<Rating> getAllRatings();

    @GetMapping("/api/rating/user/{userId}")
    List<Rating> getRatingsByUserId(@PathVariable String userId);

    @GetMapping("/api/rating/hotel/{hotelId}")
    List<Rating> getRatingsByHotelId(@PathVariable String hotelId);
}
