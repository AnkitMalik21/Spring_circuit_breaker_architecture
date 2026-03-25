package com.icwd.user_service.service;

import com.icwd.user_service.dto.UserRequest;
import com.icwd.user_service.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    //user operation

    //create
    User saveUser(UserRequest userRequest);

    //get all User
    List<User> getAllUser();

    User getUser(Long userId);


    //update
    User updateUser(Long userId, UserRequest request);

    //delete
    String deleteUser(Long userId);
}
