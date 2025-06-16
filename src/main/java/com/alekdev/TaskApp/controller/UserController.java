package com.alekdev.TaskApp.controller;

import com.alekdev.TaskApp.dto.Response;
import com.alekdev.TaskApp.dto.UserRequest;
import com.alekdev.TaskApp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response<?>> signUp(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.signUp(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<Response<?>> login(@Valid @RequestBody UserRequest userRequest){
        return ResponseEntity.ok(userService.login(userRequest));
    }

}
