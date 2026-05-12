package com.newshub.NewsHub.dto.authDTO;

import lombok.Data;

/**
 * Dto для аутентификации пользователя (вход в систему используя логин и пароль)
 */
@Data
public class AuthRequestDto {
    private String username;
    private String password;
}
