package com.newshub.NewsHub.service;

import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import com.newshub.NewsHub.dto.userDTO.UserUpdateRequestDTO;

import java.util.Set;

public interface UserToUserService {

    public UserResponseDTO userUpdate(UserUpdateRequestDTO userUpdateRequestDTO);

    public UserResponseDTO addInterestToUser(String interest);
    public UserResponseDTO removeInterestToUser(String interest);
    public Set<String> getUserInterests();
}
