package com.icwd.hotel.controller;

import com.icwd.hotel.entity.Hotel;
import com.icwd.hotel.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    //create
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
    }

    //getAll
    @GetMapping
    public ResponseEntity<List<Hotel>> getAllHotel(){
        List<Hotel> list = hotelService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    //getOne
    @GetMapping("/{id}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String id){
        Hotel hotel = hotelService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }
}
