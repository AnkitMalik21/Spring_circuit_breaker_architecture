package com.icwd.user_service.Controller;

import com.icwd.user_service.dto.UserRequest;
import com.icwd.user_service.entity.User;
import com.icwd.user_service.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final AtomicInteger retryCount = new AtomicInteger(1);

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        List<User> list = userService.getAllUser();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/{id}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    @Retry(name = "ratingHotelService")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "rateLimiterFallback")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        log.info("Get single User Handler: UserController");
        log.info("Retry count: {}", retryCount.getAndIncrement());
        User user = userService.getUser(id);

        // Reset it if successfull so the next request starts at 1
        retryCount.set(1);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    // creating fall back method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex) {
        log.info("Fallback is not working because service is down", ex.getMessage());
        // log.info("the retry count is {}",retryCount.getAndIncrement());
        User user = User.builder()
                .email("dummy@gmail.com")
                .name("dummy")
                .about("this is a dummy user because some service is down")
                .userId("123455")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // creating fall back method for rate limiter - returns 429 Access Denied
    public ResponseEntity<User> rateLimiterFallback(String userId, Exception ex) {
        log.info("Rate limit exceeded -> Access Denied for userId: {}", userId);
        User user = User.builder()
                .email("rate-limited@gmail.com")
                .name("Rate Limited")
                .about("Access Denied: Too many requests. Please try again later.")
                .userId(userId)
                .build();
        return new ResponseEntity<>(user, HttpStatus.TOO_MANY_REQUESTS);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody UserRequest userRequest) {
        User user = userService.updateUser(id, userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        String str = userService.deleteUser(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(200)).body(str);
    }

}

/**
 * Feign Client
 * the feign is a declarative Http web client developed by netflix
 * if you want to use Feign, create an interface and annotate it.
 */

/**
 * Because you have max-attempts: 3 in your Retry configuration,
 * every time you make 1 HTTP request to your endpoint,
 * Spring Boot internally attempts to execute the getUserById method 3 times (1
 * initial try + 2 retries).
 * 
 * Since the @CircuitBreaker is wrapped around the
 * 
 * @Retry (thanks to the aspect-order properties),
 *        the Circuit Breaker only evaluates the result after all 3 retries have
 *        finished and failed. Therefore, to the Circuit Breaker, those 3
 *        internal attempts only count as 1 call.
 * 
 *        Because your Circuit Breaker requires minimum-number-of-calls: 5:
 * 
 *        You must make at least 5 separate HTTP requests that fail.
 *        5 HTTP requests × 3 attempts each = 15 total entries into your method
 *        logic.
 *        So yes, your retryCount log will reach 15 before the Circuit Breaker
 *        even begins calculating the failure rate (and subsequently trips
 *        OPEN).
 * 
 *        Try making those 5 requests (so that the retryCount hits 15), and you
 *        will see the Circuit Breaker open up!
 */
