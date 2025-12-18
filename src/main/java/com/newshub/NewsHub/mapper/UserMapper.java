package com.newshub.NewsHub.mapper;

import com.newshub.NewsHub.dto.UserDTO;
import com.newshub.NewsHub.model.User;
import org.springframework.stereotype.Component;

/**
 * Преобразование сущности User в UserDTO
 */

@Component
public class UserMapper {

    public UserDTO UserToUserDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setStatus(user.getStatus());
        return userDTO;
    }

}
