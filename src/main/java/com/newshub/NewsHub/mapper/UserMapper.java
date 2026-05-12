package com.newshub.NewsHub.mapper;

import com.newshub.NewsHub.dto.userDTO.UserCreateUpdateRequestDto;
import com.newshub.NewsHub.dto.userDTO.UserUpdateRequestDTO;
import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import com.newshub.NewsHub.model.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Преобразование сущности User в UserDTO
 */

@Component
public class UserMapper {

    private final BCryptPasswordEncoder passwordEncoder;

    public UserMapper(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toUserEntity(UserUpdateRequestDTO userUpdateRequestDTO) {
        User user = new User();
        user.setUsername(userUpdateRequestDTO.getDisplayName());
        user.setEmail(userUpdateRequestDTO.getEmail());
        if (userUpdateRequestDTO.getInterests() != null) {
            user.setInterests(userUpdateRequestDTO.getInterests());
        }
        return user;
    }

    public User toUserEntity(UserCreateUpdateRequestDto userCreateUpdateRequestDto) {
        User user = new User();
        user.setUsername(userCreateUpdateRequestDto.getUsername());
        user.setUsername(userCreateUpdateRequestDto.getDisplayName());
        user.setEmail(userCreateUpdateRequestDto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(userCreateUpdateRequestDto.getPassword()));
        if (userCreateUpdateRequestDto.getInterests() != null) {
            user.setInterests(userCreateUpdateRequestDto.getInterests());
        }
        return user;
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setDisplayName(user.getDisplayName());
        userResponseDTO.setStatus(user.getStatus());

        if (user.getInterests() != null) {
            userResponseDTO.setInterests(user.getInterests());
        }

        return userResponseDTO;
    }

}
