package io.github.sustainable.marketplace.controller;


import io.github.sustainable.marketplace.dto.request.LoginRequest;
import io.github.sustainable.marketplace.dto.request.RegisterRequest;
import io.github.sustainable.marketplace.dto.response.ApiResponse;
import io.github.sustainable.marketplace.dto.response.UserDto;
import io.github.sustainable.marketplace.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
//@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        UserDto userDto = userService.register(request);
        return ResponseEntity.ok("User successfully registered with username " + userDto.getUsername());
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDto>> login(@RequestBody LoginRequest request) {
        ApiResponse<UserDto> response = userService.login(request);
        if (response.getData() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(response);
    }
}