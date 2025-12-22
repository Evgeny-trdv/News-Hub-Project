package com.newshub.NewsHub.service.impl;

import com.newshub.NewsHub.dto.userDTO.UserRequestDTO;
import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import com.newshub.NewsHub.exception.BusinessException;
import com.newshub.NewsHub.exception.ResourceNotFoundException;
import com.newshub.NewsHub.mapper.UserMapper;
import com.newshub.NewsHub.model.User;
import com.newshub.NewsHub.repository.UserRepository;
import com.newshub.NewsHub.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Сервис для работы с пользователями (User)
 */

@Service
public class UserServiceImpl implements UserService {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Метод получения пользователя (DTO) с помощью id
     * @param userId id пользователя
     * @return UserResponseDTO
     */
    @Override
    public UserResponseDTO getUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isPresent()) {
            logger.info("User found with user id: {}", userId);
            return userMapper.toUserResponseDTO(user.get());
        } else {
            throw new ResourceNotFoundException("User", "id", userId);
        }
    }

    /**
     * Метод получения пользователя (DTO) с помощью id
     * @param username username пользователя
     * @return UserResponseDTO
     */
    @Override
    public UserResponseDTO getUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", username));

        logger.info("User found with username: {}", username);
        return userMapper.toUserResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("Users not found");
        }

        logger.info("Users found");
        List<UserResponseDTO> userResponseDTOs = users
                .stream()
                .map(userMapper::toUserResponseDTO)
                .toList();

        return userResponseDTOs;
    }

    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toUserResponseDTO);
    }

    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            throw new ResourceNotFoundException("UserRequestDTO is null");
        }

        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new ResourceNotFoundException("Username already exists" + userRequestDTO.getUsername());
        }

        if (!validateEmail(userRequestDTO.getEmail())) {
            throw new BusinessException("Email is not valid" + userRequestDTO.getEmail());
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResourceNotFoundException("Email already exists" + userRequestDTO.getEmail());
        }

        User savedUser = userMapper.toUserEntity(userRequestDTO);
        userRepository.save(savedUser);
        return userMapper.toUserResponseDTO(savedUser);
    }

    private boolean validateEmail(String email) {
        return PATTERN.matcher(email).matches();
    }
}
