package io.github.sustainable.marketplace.service;

import io.github.sustainable.marketplace.dto.request.LoginRequest;
import io.github.sustainable.marketplace.dto.request.RegisterRequest;
import io.github.sustainable.marketplace.dto.response.ApiResponse;
import io.github.sustainable.marketplace.dto.response.UserDto;
import io.github.sustainable.marketplace.entity.User;
import io.github.sustainable.marketplace.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto register(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(request.getPassword()); // TODO: hash password
        user.setUsername(request.getUsername());

        user = userRepository.save(user);
        return mapToDto(user);
    }

//    @Override
//    public UserDto login(LoginRequest request) {
//        User user = userRepository.findByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
//
//        // TODO: validate passwordHash
//        return mapToDto(user);
//    }

    @Override
    public ApiResponse<UserDto> login(LoginRequest request) {
        // find user by email
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            return new ApiResponse<>("Failed to authenticate: user not found", null);
        }

        User user = userOptional.get();

        // ⚠️ For now plain-text check, but should be hashed (BCrypt)
        if (!user.getPasswordHash().equals(request.getPassword())) {
            return new ApiResponse<>("Failed to authenticate: invalid password", null);
        }

        // If valid
        UserDto userDto = mapToDto(user);
        return new ApiResponse<>("User successfully authenticated", userDto);
    }

    @Override
    public UserDto getUserById(UUID userId) {
        return userRepository.findById(userId)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        return dto;
    }
}