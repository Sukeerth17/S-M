package io.github.sustainable.marketplace.service;


import io.github.sustainable.marketplace.dto.request.LoginRequest;
import io.github.sustainable.marketplace.dto.request.RegisterRequest;
import io.github.sustainable.marketplace.dto.response.ApiResponse;
import io.github.sustainable.marketplace.dto.response.UserDto;

import java.util.UUID;

public interface UserService {
    UserDto register(RegisterRequest request);
    //UserDto login(LoginRequest request);
    UserDto getUserById(UUID userId);
    ApiResponse<UserDto> login(LoginRequest request);
}