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
        if (userRequestDTO == null) {
            return null;
        }

        User user = new User();
        user.setUsername(userRequestDTO.getUsername());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(userRequestDTO.getPassword());
        user.setStatus(userRequestDTO.getStatus());

        if (userRequestDTO.getCategories() != null) {
            user.setCategories(userRequestDTO.getCategories());
        }
        return user;
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setStatus(user.getStatus());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        userResponseDTO.setCategories(user.getCategories());
        return userResponseDTO;
    }

}
