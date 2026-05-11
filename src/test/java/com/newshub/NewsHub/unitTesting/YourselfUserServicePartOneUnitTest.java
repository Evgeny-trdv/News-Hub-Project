package com.newshub.NewsHub.unitTesting;

import com.newshub.NewsHub.dto.userDTO.UserCreateUpdateRequestDto;
import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import com.newshub.NewsHub.exception.BusinessException;
import com.newshub.NewsHub.exception.ResourceNotFoundException;
import com.newshub.NewsHub.mapper.UserMapper;
import com.newshub.NewsHub.model.Role;
import com.newshub.NewsHub.model.User;
import com.newshub.NewsHub.model.UserStatus;
import com.newshub.NewsHub.repository.UserRepository;
import com.newshub.NewsHub.service.impl.ExternalUserServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.regex.Pattern;

/**
 * модульные тесты (unit) класса YourselfUserService (part one)
 * для методов getUser(Long userId), getUser(String username), getAllUsers()
 */
@ExtendWith(MockitoExtension.class)
public class YourselfUserServicePartOneUnitTest {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_REGEX);

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private ExternalUserServiceImpl externalUserService;

    private User user1;
    private UserResponseDTO user1Dto;
    private User user2;
    private UserResponseDTO user2Dto;
    private List<User> listUsers;
    private List<UserResponseDTO> listUsersDto;

    @BeforeEach
    void setUp() {
        user1 = new User(
                "Sabrina",
                "sab_rin@mail.ru",
                "Sabrina",
                "1234567",
                Role.USER
        );
        user1.setId(1L);
        user1Dto = new UserResponseDTO(
                1L,
                "Sabrina",
                "sab_rin@mail.ru",
                "Sabrina",
                UserStatus.ACTIVE,
                new HashSet<>(Set.of("GENERAL")));

        user2 = new User(
                "Alex",
                "alex_cool@gmail.com",
                "Alex",
                "1234567",
                Role.ADMIN
        );
        user2.setId(2L);
        user2Dto = new UserResponseDTO(
                2L,
                "Alex",
                "alex_cool@gmail.com",
                "Alex",
                UserStatus.ACTIVE,
                new HashSet<>(Set.of("GENERAL")));

        listUsers = new ArrayList<>(List.of(user1, user2));
        listUsersDto = new ArrayList<>(List.of(user1Dto, user2Dto));

    }

    @Test
    public void getUserMethodByUserIdShouldReturnUser() {
        Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.ofNullable(user1));
        Mockito.when(userMapper.toUserResponseDTO(user1))
                .thenReturn(user1Dto);

        Assertions.assertEquals(user1Dto, externalUserService.getUser(1L));
    }

    @Test
    public void getUserMethodByUserIdShouldThrowResourceNotFoundException() {
        Mockito.when(userRepository.findById(3L))
                        .thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> externalUserService.getUser(3L));
    }

    @Test
    public void getUserMethodByUsernameShouldReturnUser() {
        Mockito.when(userRepository.findByUsername("Alex"))
                .thenReturn(Optional.ofNullable(user2));
        Mockito.when(userMapper.toUserResponseDTO(user2))
                .thenReturn(user2Dto);

        Assertions.assertEquals(user2Dto, externalUserService.getUser("Alex"));
    }

    @Test
    public void getUserMethodByUsernameShouldThrowResourceNotFoundException() {
        Mockito.when(userRepository.findByUsername("Max"))
                .thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> externalUserService.getUser("Max"));
    }

    @Test
    public void getAllUsersMethodShouldReturnAllUsers() {
        Mockito.when(userRepository.findAll()).thenReturn(listUsers);
        Mockito.when(userMapper.toUserResponseDTO(listUsers.get(0))).thenReturn(user1Dto);
        Mockito.when(userMapper.toUserResponseDTO(listUsers.get(1))).thenReturn(user2Dto);

        Assertions.assertEquals(listUsersDto, externalUserService.getAllUsers());
        Assertions.assertEquals(user1Dto, externalUserService.getAllUsers().get(0));
    }

    @Test
    public void getAllUsersMethodShouldReturnEmptyListAndThrowResourceNotFoundException() {
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> externalUserService.getAllUsers());
    }
}
