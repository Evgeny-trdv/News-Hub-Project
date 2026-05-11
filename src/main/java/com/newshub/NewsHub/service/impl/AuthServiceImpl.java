package com.newshub.NewsHub.service.impl;

import com.newshub.NewsHub.dto.authDTO.AuthRequestDto;
import com.newshub.NewsHub.dto.authDTO.AuthResponseDto;
import com.newshub.NewsHub.dto.authDTO.JwtAuthenticationDto;
import com.newshub.NewsHub.dto.authDTO.RegisterRequestDto;
import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import com.newshub.NewsHub.exception.BusinessException;
import com.newshub.NewsHub.mapper.UserMapper;
import com.newshub.NewsHub.model.Role;
import com.newshub.NewsHub.model.User;
import com.newshub.NewsHub.repository.UserRepository;
import com.newshub.NewsHub.security.CustomUserDetails;
import com.newshub.NewsHub.security.CustomUserServiceImpl;
import com.newshub.NewsHub.security.jwt.JwtTokenProvider;
import com.newshub.NewsHub.service.AuthService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для управления аутентификацией пользователей.
 *
 * Отвечает за:
 * 1. Регистрацию новых пользователей
 * 2. Аутентификацию существующих пользователей
 * 3. Обновление JWT токенов
 * 4. Выход пользователя из системы
 * 5. Получение текущего пользователя
 */
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final CustomUserServiceImpl customUserService;
    private final RabbitTemplate rabbitTemplate;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, CustomUserServiceImpl customUserService, RabbitTemplate rabbitTemplate) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.customUserService = customUserService;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Аутентифицирует пользователя по логину/email и паролю.
     *
     * Алгоритм:
     * 1. Определяем, является ли входной параметр email или username
     * 2. Находим пользователя в базе данных
     * 3. Проверяем статус пользователя
     * 4. Аутентифицируем через Spring Security
     * 5. Генерируем JWT токены
     *
     */
    @Override
    @Transactional
    public AuthResponseDto login(AuthRequestDto authRequestDto) {

        CustomUserDetails customUserDetails = (CustomUserDetails) customUserService.loadUserByUsername(authRequestDto.getUsername());
        User user = customUserDetails.user();

        // Аутентификация через Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        authRequestDto.getPassword()));

        // Устанавливаем аутентификацию в контекст безопасности
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtAuthenticationDto jwtAuth = jwtTokenProvider.generateAuthToken(authentication);

        UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(user);

        return new AuthResponseDto(
                jwtAuth.getToken(),
                jwtAuth.getRefreshToken(),
                userResponseDTO
        );
    }

    /**
     * Регистрирует нового пользователя в системе.
     *
     * Алгоритм:
     * 1. Проверяем уникальность username и email
     * 2. Проверяем совпадение паролей
     * 3. Создаем нового пользователя
     * 4. Хэшируем пароль
     * 5. Устанавливаем дефолтные значения
     * 6. Сохраняем пользователя
     * 7. Отправляем сообщение на почту
     * 8. Аутентифицируем и генерируем токены
     *
     * @param registerRequestDto DTO с данными для регистрации
     * @return AuthResponseDTO с токенами и информацией о пользователе
     */
    @Override
    @Transactional
    public AuthResponseDto register(RegisterRequestDto registerRequestDto) {
        if (userRepository.existsByUsername(registerRequestDto.getUsername())) {
            throw new BusinessException("Username is already in use");
        }

        if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
            throw new BusinessException("Email is already in use");
        }

        if (!registerRequestDto.getPassword().equals(registerRequestDto.getConfirmPassword())) {
            throw new BusinessException("Passwords do not match");
        }

        User user = new User(
                registerRequestDto.getUsername(),
                registerRequestDto.getEmail(),
                registerRequestDto.getDisplayName() != null
                        ? registerRequestDto.getDisplayName()
                        : registerRequestDto.getUsername(),
                passwordEncoder.encode(registerRequestDto.getPassword()),
                Role.USER);

        userRepository.save(user);

        rabbitTemplate.convertAndSend("notification-exchange", "email", registerRequestDto.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        registerRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtAuthenticationDto jwtAuth = jwtTokenProvider.generateAuthToken(authentication);

        UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(user);

        return new AuthResponseDto(
                jwtAuth.getToken(),
                jwtAuth.getRefreshToken(),
                userResponseDTO
        );
    }

    @Override
    public void logout() {
        //работает
        SecurityContextHolder.clearContext();
    }

    @Override
    public UserResponseDTO getCurrentUser() {
        //работает
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BadCredentialsException("User not authenticated");
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof CustomUserDetails) {
            User user = ((CustomUserDetails) principal).user();
            return userMapper.toUserResponseDTO(user);
        } else if (principal instanceof User) {
            return userMapper.toUserResponseDTO((User) principal);
        }
        throw new BadCredentialsException("Invalid principal type");
    }
}
