package com.icwd.user_service.service;

import com.icwd.user_service.dto.UserRequest;
import com.icwd.user_service.entity.Hotel;
import com.icwd.user_service.entity.Rating;
import com.icwd.user_service.entity.User;
import com.icwd.user_service.exception.ResourceNotFoundException;
import com.icwd.user_service.feign.HotelService;
import com.icwd.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final HotelService hotelService;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
//        User user = User.builder()
//                .name(userRequest.getName())
//                .email(userRequest.getEmail())
//                .about(userRequest.getAbout())
//                .build();
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

//    @Override
//    public User getUser(String userId) {
//        User user =  userRepository.findById(userId).orElseThrow(
//                ()->new ResourceNotFoundException("Did not found the user with mentioned userId"));
//
//        //get rating for the above user from rating service
//        //http://localhost:8083/api/rating/user/239c0eee-0cd8-4f88-b35c-0c500270ce00
//
//        ArrayList<Rating> ratingOfUser = restTemplate.getForObject("http://localhost:8083/api/rating/user/"+user.getUserId(), ArrayList.class);
//        logger.info("{}",ratingOfUser);
//
//        List<Rating> ratingList = ratingOfUser.stream().map(rating -> {
//            //api call to hotel service to get the hotel
//            // http://localhost:8082/api/hotel/9e7e7e6c-d0d3-4e62-a285-38d156925e1d
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://localhost:8082/api/hotel/"+rating.getHotelId(), Hotel.class);
//
//            Hotel hotel = forEntity.getBody();
//            logger.info("response status code: {} ", forEntity.getStatusCode());
//
//            //set the hotel to rating
//            rating.setHotel(hotel);
//            //return the rating
//            return rating;
//
//        }).collect(Collectors.toList());
//
//        user.setRatings(ratingList);
//        return user;
//
//    }

    @Override
    public User getUser(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("Did not found the user with mentioned userId"));

        List<Rating> ratingOfUser = new ArrayList<>();

        try{
            // 2. call rating service safely
            //way to fetch rating
            ResponseEntity<List<Rating>> response = restTemplate.exchange(
                    "http://RATINGSERVICE/api/rating/user/" + user.getUserId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Rating>>() {}
            );

            //3 Null safety
            ratingOfUser = Optional.ofNullable(response.getBody())
                    .orElse(new ArrayList<>());

        } catch (Exception e) {

            logger.error("Error while fetching rating for user {}: {}",user, e.getMessage());
            throw new RuntimeException(e);
        }

        logger.info("{}",ratingOfUser);

        List<Rating> ratingList = ratingOfUser.stream().map(rating -> {
            try{
                /**ResponseEntity<Hotel> hotelResponse = restTemplate.getForEntity(
                        "http://HOTELSERVICE/api/hotel/"+rating.getHotelId(),
                        Hotel.class
                );

                Hotel hotel = hotelResponse.getBody();

                 */
                Hotel hotel = hotelService.getHotel(rating.getHotelId());
                if(hotel != null){
                    rating.setHotel(hotel);
                }
            } catch (Exception e) {
                logger.error("Error while fetching hotel for rating {} : {}",rating.getRatingId(),e.getMessage());
            }

            return rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;
    }

    @Override
    public User updateUser(String userId, UserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("Did not found the user with mentioned userId"));


        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAbout(request.getAbout());

        return userRepository.save(user);
    }

    @Override
    public String deleteUser(String userId) {
         User user = userRepository.findById(userId)
                 .orElseThrow(()->new ResourceNotFoundException("Did not found the user with mentioned userId"));
         userRepository.deleteById(userId);
         return "User with the " + userId + " has been deleted";
    }
}
