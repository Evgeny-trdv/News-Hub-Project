package com.newshub.NewsHub.dto.authDTO;

import lombok.Data;

@Data
public class JwtAuthenticationDto {

    private String token;
    private String refreshToken;
}
