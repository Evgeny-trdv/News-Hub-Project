package com.newshub.NewsHub.service;

import com.newshub.NewsHub.dto.authDTO.AuthRequestDto;
import com.newshub.NewsHub.dto.authDTO.AuthResponseDto;
import com.newshub.NewsHub.dto.authDTO.ChangePasswordRequest;
import com.newshub.NewsHub.dto.authDTO.RegisterRequestDto;
import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import com.newshub.NewsHub.model.User;

public interface AuthService {

    public AuthResponseDto login(AuthRequestDto authRequestDto);

    public AuthResponseDto register(RegisterRequestDto registerRequestDto);

    public void logout();

    public UserResponseDTO getCurrentUser();

    public void changePassword(ChangePasswordRequest changePassword);
}
