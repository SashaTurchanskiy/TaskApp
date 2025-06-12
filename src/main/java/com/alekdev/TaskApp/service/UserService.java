package com.alekdev.TaskApp.service;

import com.alekdev.TaskApp.dto.Response;
import com.alekdev.TaskApp.dto.UserRequest;
import com.alekdev.TaskApp.entity.User;

public interface UserService {

    Response<?> signUp(UserRequest userRequest);
    Response<?> login(UserRequest userRequest);
    User getCurrentLoggedInUser();
}
