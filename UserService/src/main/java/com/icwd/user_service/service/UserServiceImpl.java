package com.icwd.user_service.service;

import com.icwd.user_service.dto.UserRequest;
import com.icwd.user_service.entity.User;
import com.icwd.user_service.exception.ResourceNotFoundException;
import com.icwd.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public User saveUser(UserRequest userRequest) {
        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .about(userRequest.getAbout())
                .build();

        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("Did not found the user with mentioned userId"));
    }

    @Override
    public User updateUser(Long userId, UserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("Did not found the user with mentioned userId"));


        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setAbout(request.getAbout());

        return userRepository.save(user);
    }

    @Override
    public String deleteUser(Long userId) {
         User user = userRepository.findById(userId)
                 .orElseThrow(()->new ResourceNotFoundException("Did not found the user with mentioned userId"));
         userRepository.deleteById(userId);
         return "User with the " + userId + " has been deleted";
    }
}
