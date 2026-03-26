package com.icwd.hotel.controller;

import com.icwd.hotel.entity.Hotel;
import com.icwd.hotel.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hotel")
@RequiredArgsConstructor
public class HotelController {

    private HotelService hotelService;

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
    public ResponseEntity<Hotel> getHotelById(@PathVariable )
}
