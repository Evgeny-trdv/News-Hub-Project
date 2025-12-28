package com.newshub.NewsHub.service.impl;

import com.newshub.NewsHub.dto.userDTO.UserRequestDTO;
import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import com.newshub.NewsHub.exception.BusinessException;
import com.newshub.NewsHub.exception.ResourceNotFoundException;
import com.newshub.NewsHub.mapper.UserMapper;
import com.newshub.NewsHub.model.User;
import com.newshub.NewsHub.model.UserStatus;
import com.newshub.NewsHub.repository.UserRepository;
import com.newshub.NewsHub.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    /**
     * Метод получения списка всех пользователей
     * @return список пользователей (user)
     */
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

    /**
     * Метод получения списка всех пользователей через пагинацию (по странично)
     * @param pageable объекст постраничного запроса
     * @return список пользователей (user)
     */
    @Override
    public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(userMapper::toUserResponseDTO);
    }

    /**
     * Метод создания (сохранения) пользователя в базу данных
     * @param userRequestDTO DTO пользователя для создания/обновления
     * @return DTO пользователя для получения ответа
     */
    @Override
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            throw new BusinessException("UserRequestDTO is null");
        }

        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new BusinessException("Username already exists" + userRequestDTO.getUsername());
        }

        if (!validateEmail(userRequestDTO.getEmail())) {
            throw new BusinessException("Email is not valid" + userRequestDTO.getEmail());
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new ResourceNotFoundException("Email already exists" + userRequestDTO.getEmail());
        }

        User savedUser = userMapper.toUserEntity(userRequestDTO);
        userRepository.save(savedUser);

        logger.info("User {} saved to database", savedUser);

        return userMapper.toUserResponseDTO(savedUser);
    }


    /**
     * Метод обновления данных пользователя
     * @param userId id пользователя
     * @param userRequestDTO DTO пользователя для создания/обновления
     * @return DTO пользователя для получения ответа
     */
    @Override
    @Transactional
    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (userRequestDTO.getUsername() != null &&
                userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new BusinessException("Username already exists" + userRequestDTO.getUsername());
        }

        if (userRequestDTO.getEmail() != null &&
                userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new BusinessException("Email already exists" + userRequestDTO.getEmail());
        }

        if (!validateEmail(userRequestDTO.getEmail())) {
            throw new BusinessException("Email is not valid" + userRequestDTO.getEmail());
        }

        if (userRequestDTO.getUsername() != null) {
            user.setUsername(userRequestDTO.getUsername());
        }

        if (userRequestDTO.getEmail() != null) {
            user.setEmail(userRequestDTO.getEmail());
        }

        if (userRequestDTO.getPassword() != null) {
            user.setPassword(userRequestDTO.getPassword());
        }

        if (userRequestDTO.getInterests() != null) {
            user.setInterests(userRequestDTO.getInterests());
        }

        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        return userMapper.toUserResponseDTO(user);
    }

    /**
     * Метод удаления пользователя из базы данных
     * @param userId id пользователя
     */
    @Override
    @Transactional
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }

        logger.info("User {} deleted to database", userId);
        userRepository.deleteById(userId);
    }

    /**
     * Метод пометки для удаления пользователя
     * @param userId id пользователя
     */
    @Override
    @Transactional
    public void deleteUserWithAbilityReturn(Long userId) {
        User userForDeleted = findUserById(userId);
        userForDeleted.setStatus(UserStatus.DELETED);
        userRepository.save(userForDeleted);
    }

    /**
     * Метод добавления новостного интереса пользователя
     * @param userId id пользователя
     * @param interest новостной интерес
     * @return DTO пользователя для получения ответа
     */
    @Override
    @Transactional
    public UserResponseDTO addInterestToUser(Long userId, String interest) {
        User findedUser = findUserById(userId);
        findedUser.addCategory(StringUtils.capitalize(interest.trim().toLowerCase()));
        userRepository.save(findedUser);
        return userMapper.toUserResponseDTO(findedUser);
    }

    /**
     * Метод удаления новостного интереса пользователя
     * @param userId id пользователя
     * @param interest новостной интерес
     * @return DTO пользователя для получения ответа
     */
    @Override
    @Transactional
    public UserResponseDTO removeInterestToUser(Long userId, String interest) {
        User findedUser = findUserById(userId);
        findedUser.removeCategory(correctStringInterest(interest));
        userRepository.save(findedUser);
        return userMapper.toUserResponseDTO(findedUser);
    }

    /**
     * Метод получения множества новостных интересов пользователя
     * @param userId id пользователя
     * @return DTO пользователя для получения ответа
     */
    @Override
    public Set<String> getUserInterests(Long userId) {
        User findedUser = findUserById(userId);
        return findedUser.getInterests();
    }

    /**
     * Метод удаления пользователя из БД по расписанию
     */
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void deleteUserForScheduled() {
        List<User> listUsersForDeleted = userRepository.listUserForDeleteWithStatusDeleted();

        for (User user : listUsersForDeleted) {
            logger.info("User {} deleted to database", user.getId());
            if (user.getUpdatedAt().plusDays(10).isAfter(LocalDateTime.now())) {
                userRepository.deleteById(user.getId());
            }
        }
    }

    private boolean validateEmail(String email) {
        return PATTERN.matcher(email).matches();
    }

    private String correctStringInterest(String interest) {
        return StringUtils.capitalize(interest.trim().toLowerCase());
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }
}
