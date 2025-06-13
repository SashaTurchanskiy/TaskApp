package com.alekdev.TaskApp.service.impl;

import com.alekdev.TaskApp.dto.Response;
import com.alekdev.TaskApp.dto.UserRequest;
import com.alekdev.TaskApp.entity.User;
import com.alekdev.TaskApp.enums.Role;
import com.alekdev.TaskApp.exception.BadRequestException;
import com.alekdev.TaskApp.exception.NotFoundException;
import com.alekdev.TaskApp.repo.UserRepo;
import com.alekdev.TaskApp.security.JwtUtils;
import com.alekdev.TaskApp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public Response<?> signUp(UserRequest userRequest) {
        log.info("Inside signUp()");
        Optional<User> existingUser = userRepo.findByUserName(userRequest.getUserName());

        if (existingUser.isPresent()){
            throw new BadRequestException("User already exists with username: " + userRequest.getUserName());
        }

        User user = new User();
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        user.setUserName(userRequest.getUserName());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        //save user
        userRepo.save(user);

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("User registered successfully")
                .build();
    }

    @Override
    public Response<?> login(UserRequest userRequest) {
        log.info("Inside login()");

        User user = userRepo.findByUserName(userRequest.getUserName())
                .orElseThrow(()-> new BadRequestException("User not found with username: " + userRequest.getUserName()));

        if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
            throw new BadRequestException("Invalid password for user: " + userRequest.getUserName());
        }
        String token = jwtUtils.generateToken(user.getUserName());

        return Response.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Login successful")
                .data(token)
                .build();
    }

    @Override
    public User getCurrentLoggedInUser() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepo.findByUserName(username)
                .orElseThrow(() -> new NotFoundException("User not found with username: " + username));
    }
}
