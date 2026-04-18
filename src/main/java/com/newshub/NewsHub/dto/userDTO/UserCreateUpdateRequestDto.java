package com.newshub.NewsHub.dto.userDTO;

import lombok.Data;

import java.util.Set;

/**
 * DTO для создания/обновления данных пользователя (изменения со стороны администратора)
 */
@Data
public class UserCreateUpdateRequestDto {

    private String username;
    private String email;
    private String displayName;
    private String password;
    private Set<String> interests;
}
