package com.icwd.user_service.service;

import com.icwd.user_service.dto.UserRequest;
import com.icwd.user_service.entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    //user operation

    //create
    User saveUser(User user);

    //get all User
    List<User> getAllUser();

    User getUser(String userId);


    //update
    User updateUser(String userId, UserRequest request);

    //delete
    String deleteUser(String userId);
}
