package com.icwd.user_service.feign;

import com.icwd.user_service.entity.Hotel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="HOTELSERVICE")
public interface HotelService {
    @GetMapping("/api/hotel/{hotelId}")
    Hotel getHotel(@PathVariable("hotelId") String hotelId);
}

//method in interface are already public
