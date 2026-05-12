package com.newshub.NewsHub.controller;

import com.newshub.NewsHub.dto.authDTO.AuthRequestDto;
import com.newshub.NewsHub.dto.authDTO.AuthResponseDto;
import com.newshub.NewsHub.dto.authDTO.ChangePasswordRequest;
import com.newshub.NewsHub.dto.authDTO.RegisterRequestDto;
import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import com.newshub.NewsHub.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "APIs for user authentication and registration")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody AuthRequestDto authRequestDto) {
        return authService.login(authRequestDto);
    }

    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody RegisterRequestDto registerRequestDto) {
        return authService.register(registerRequestDto);
    }

    @GetMapping("/logout")
    public void logout() {
        authService.logout();
    }

    @GetMapping("/me")
    public UserResponseDTO me() {
        return authService.getCurrentUser();
    }

    @PostMapping("/change-password")
    public void changePassword(@RequestBody ChangePasswordRequest changePassword) {
        authService.changePassword(changePassword);
    }
}
