package com.newshub.NewsHub.security.jwt;

import com.newshub.NewsHub.dto.authDTO.JwtAuthenticationDto;
import com.newshub.NewsHub.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwt.secret}")
    private String JwtSecret;

    public JwtAuthenticationDto generateAuthToken(Authentication authentication) {
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setToken(generateJwtToken(authentication));
        jwtAuthenticationDto.setRefreshToken(generateRefreshToken(authentication));
        return jwtAuthenticationDto;
    }

    public JwtAuthenticationDto refreshBaseToken(Authentication authentication, String refreshToken) {
        JwtAuthenticationDto jwtAuthenticationDto = new JwtAuthenticationDto();
        jwtAuthenticationDto.setToken(generateJwtToken(authentication));
        jwtAuthenticationDto.setRefreshToken(refreshToken);
        return jwtAuthenticationDto;
    }

    public Long getUserIdFromToken(String token) {
        // Парсим токен и извлекаем claims (утверждения)
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // Проверяем подпись
                .build()
                .parseClaimsJws(token)          // Парсим подписанный JWT
                .getBody();                     // Получаем claims

        // Subject содержит ID пользователя как строку
        return Long.parseLong(claims.getSubject());
    }

    public String getUsernameFromToken(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.get("username", String.class);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token - некорректный формат токена");
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token - токен просрочен");
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token - неподдерживаемый тип токена");
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty - пустая строка токена");
        } catch (SecurityException ex) {
            LOGGER.error("JWT signature validation failed - ошибка проверки подписи");
        }
        return false;
    }

    private String generateJwtToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Date date = Date
                .from(LocalDateTime
                        .now()
                        .plusDays(1)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.user().getId()))
                .claim("username", userDetails.getUsername())
                .claim("email", userDetails.user().getEmail())
                .claim("authorities", userDetails.getAuthorities())
                .setExpiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    private String generateRefreshToken(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Date date = Date
                .from(LocalDateTime
                        .now()
                        .plusDays(5)
                        .atZone(ZoneId.systemDefault())
                        .toInstant());

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.user().getId()))
                .claim("type", "refresh")
                .setExpiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(JwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
