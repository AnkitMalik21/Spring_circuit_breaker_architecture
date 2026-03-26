package com.icwd.hotel.service;

import com.icwd.hotel.entity.Hotel;

import java.util.List;

public interface HotelService {
    //create
    Hotel create (Hotel hotel);

    //getAll
    List<Hotel> getAll();

    //get Single
    Hotel get(String id);
}
