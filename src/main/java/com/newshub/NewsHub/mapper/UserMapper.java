package com.newshub.NewsHub.mapper;

import com.newshub.NewsHub.dto.userDTO.UserCreateOrUpdateDTO;
import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import com.newshub.NewsHub.model.User;
import org.springframework.stereotype.Component;

/**
 * Преобразование сущности User в UserDTO
 */

@Component
public class UserMapper {

    public UserCreateOrUpdateDTO toUserCreateOrUpdateDTO(User user) {
        if (user == null) {
            return null;
        }

        UserCreateOrUpdateDTO userCreateOrUpdateDTO = new UserCreateOrUpdateDTO();
        userCreateOrUpdateDTO.setUsername(user.getUsername());
        userCreateOrUpdateDTO.setEmail(user.getEmail());
        userCreateOrUpdateDTO.setStatus(user.getStatus());
        return userCreateOrUpdateDTO;
    }

    public User toUser(UserCreateOrUpdateDTO userCreateOrUpdateDTO) {
        if (userCreateOrUpdateDTO == null) {
            return null;
        }

        User user = new User();
        user.setUsername(userCreateOrUpdateDTO.getUsername());
        user.setEmail(userCreateOrUpdateDTO.getEmail());
        user.setPassword(userCreateOrUpdateDTO.getPassword());
        user.setStatus(userCreateOrUpdateDTO.getStatus());
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
        userResponseDTO.setCategories(user.getInterests());
        return userResponseDTO;
    }

    public User toUser(UserResponseDTO userResponseDTO) {
        if (userResponseDTO == null) {
            return null;
        }

        User user = new User();
        user.setId(userResponseDTO.getId());
        user.setUsername(userResponseDTO.getUsername());
        user.setEmail(userResponseDTO.getEmail());
        user.setStatus(userResponseDTO.getStatus());
        user.setInterests(userResponseDTO.getCategories());
        return user;
    }

}
