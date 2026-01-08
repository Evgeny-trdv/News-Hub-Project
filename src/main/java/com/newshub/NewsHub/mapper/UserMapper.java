package com.newshub.NewsHub.mapper;

import com.newshub.NewsHub.dto.userDTO.UserRequestDTO;
import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import com.newshub.NewsHub.model.User;
import org.springframework.stereotype.Component;

/**
 * Преобразование сущности User в UserDTO
 */

@Component
public class UserMapper {

    public User toUserEntity(UserRequestDTO userRequestDTO) {
        User user = new User();

        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPasswordHash(userRequestDTO.getPassword());

        if (userRequestDTO.getInterests() != null) {
            user.setInterests(userRequestDTO.getInterests());
        }

        return user;
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setStatus(user.getStatus());
        userResponseDTO.setCreatedAt(user.getCreatedAt());

        if (user.getInterests() != null) {
            userResponseDTO.setInterests(user.getInterests());
        }

        return userResponseDTO;
    }

}
