package com.icwd.user_service;

import com.icwd.user_service.entity.Rating;
import com.icwd.user_service.feign.RatingService;
import org.junit.jupiter.api.Test;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	private RatingService ratingService;

	@Test
	void contextLoads() {
	}

//	@Test
//	void createRating(){
//		Rating rating = Rating.builder()
//				.rating(10)
//				.userId("")
//				.hotelId("")
//				.feedback("this is created using the feign client")
//				.build();
//        Rating saveRating = ratingService.create(rating);
//		System.out.println(saveRating);
//	}

}
