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
 * модульные тесты (unit) класса YourselfUserService (part two)
 * для методов  createUser(UserCreateUpdateRequestDto userCreateDto),
 *              updateUser(Long userId, UserCreateUpdateRequestDto userUpdateRequestDTO)
 */
@ExtendWith(MockitoExtension.class)
public class YourselfUserServicePartTwoUnitTest {

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
    private UserResponseDTO user1DtoWithChangedEmail;
    private UserResponseDTO user1DtoWithChangedDisplayName;
    private UserResponseDTO user1DtoWithChangedInterests;
    private UserResponseDTO user1DtoWithChangedAll;
    private UserCreateUpdateRequestDto userCreateDtoForSuccess;
    private UserCreateUpdateRequestDto userUpdateDtoForSuccessWithChangeEmail;
    private UserCreateUpdateRequestDto userUpdateDtoForSuccessWithChangeDisplayName;
    private UserCreateUpdateRequestDto userUpdateDtoForSuccessWithChangeInterests;
    private UserCreateUpdateRequestDto userUpdateDtoForSuccessWithChangeAll;
    private UserCreateUpdateRequestDto userCreateUpdateDtoForFailByEmail;
    private UserCreateUpdateRequestDto userUpdateUpdateDtoForFailByEmail;

    @BeforeEach
    void setUpForUser() {
        user1 = new User(
                "Alex",
                "alex_cool@gmail.com",
                "Alex",
                "1234567",
                Role.ADMIN
        );
        user1.setId(1L);

        User user1WithChangedEmail = user1;
        user1WithChangedEmail.setEmail("alexXx_cool@gmail.com");

        User user1WithChangedDisplayName = user1;
        user1WithChangedDisplayName.setEmail(null);
        user1WithChangedDisplayName.setDisplayName("Alexiy");

        User user1WithChangedInterests = user1;
        user1WithChangedInterests.setInterests(new HashSet<>(Set.of("General", "Pet")));

        User user1WithChangedAll = user1;
        user1WithChangedAll.setEmail("alexXx_cool@gmail.com");
        user1WithChangedAll.setDisplayName("Alexiy");
        user1WithChangedAll.setInterests(new HashSet<>(Set.of("General", "Pet")));
    }

    @BeforeEach
    public void setUpForUserDto() {
        user1Dto = new UserResponseDTO(
                1L,
                "Alex",
                "alex_cool@gmail.com",
                "Alex",
                UserStatus.ACTIVE,
                new HashSet<>(Set.of("GENERAL")));

        user1DtoWithChangedEmail = user1Dto;
        user1DtoWithChangedEmail.setEmail("alexXx_cool@gmail.com");

        user1DtoWithChangedDisplayName = user1Dto;
        user1DtoWithChangedDisplayName.setDisplayName("Alexiy");

        user1DtoWithChangedInterests = user1Dto;
        user1DtoWithChangedInterests.setInterests(new HashSet<>(Set.of("General", "Pet")));

        user1DtoWithChangedAll = user1Dto;
        user1DtoWithChangedEmail.setEmail("alexXx_cool@gmail.com");
        user1DtoWithChangedDisplayName.setDisplayName("Alexiy");
        user1DtoWithChangedDisplayName.setInterests(new HashSet<>(Set.of("General", "Pet")));

        userCreateDtoForSuccess = new UserCreateUpdateRequestDto(
                "Alex",
                "alex_cool@gmail.com",
                "Alex",
                "1234567",
                new HashSet<>(Set.of("GENERAL"))
        );

        userUpdateDtoForSuccessWithChangeEmail = new UserCreateUpdateRequestDto(
                "Alex",
                "alexXx_cool@gmail.com",
                "Alex",
                "1234567",
                new HashSet<>(Set.of("GENERAL"))
        );

        userUpdateDtoForSuccessWithChangeDisplayName = new UserCreateUpdateRequestDto(
                "Alex",
                "alex_cool@gmail.com",
                "Alexiy",
                "1234567",
                new HashSet<>(Set.of("GENERAL"))
        );

        userUpdateDtoForSuccessWithChangeInterests = new UserCreateUpdateRequestDto(
                "Alex",
                "alex_cool@gmail.com",
                "Alex",
                "1234567",
                new HashSet<>(Set.of("General", "Pet"))
        );

        userUpdateDtoForSuccessWithChangeAll = new UserCreateUpdateRequestDto(
                "Alex",
                "alexXx_cool@gmail.com",
                "Alexiy",
                "1234567",
                new HashSet<>(Set.of("General", "Pet"))
        );

        userCreateUpdateDtoForFailByEmail = new UserCreateUpdateRequestDto(
                "Alex",
                "alex_gmail.com",
                "Alex",
                "1234567",
                new HashSet<>(Set.of("GENERAL"))
        );

        userUpdateUpdateDtoForFailByEmail = new UserCreateUpdateRequestDto(
                "Alex",
                "alex_gmail.com",
                "Alexiy",
                "1234567",
                new HashSet<>(Set.of("GENERAL"))
        );
    }


    @Test
    public void createUserMethodShouldReturnSuccess() {
        Mockito.when(userRepository.existsByUsername("Alex")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("alex_cool@gmail.com")).thenReturn(false);

        Mockito.when(userMapper.toUserEntity(userCreateDtoForSuccess)).thenReturn(user1);
        Mockito.when(userRepository.save(user1)).thenReturn(user1);

        Mockito.when(userMapper.toUserResponseDTO(user1)).thenReturn(user1Dto);

        Assertions.assertEquals(user1Dto, externalUserService.createUser(userCreateDtoForSuccess));
    }

    @Test
    public void createUserMethodShouldThrowBusinessErrorBecauseDtoIsNull() {
        Assertions.assertThrows(BusinessException.class, () -> externalUserService.createUser(null));
    }

    @Test
    public void createUserMethodShouldThrowBusinessErrorBecauseUsernameExists() {
        Mockito.when(userRepository.existsByUsername("Alex")).thenReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> externalUserService.createUser(userUpdateDtoForSuccessWithChangeEmail));
    }

    @Test
    public void createUserMethodShouldThrowBusinessErrorBecauseEmailExists() {
        Mockito.when(userRepository.existsByUsername("Alex")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("alex_cool@gmail.com")).thenReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> externalUserService.createUser(userCreateDtoForSuccess));
    }

    @Test
    public void createUserMethodShouldThrowBusinessErrorBecauseEmailISNotCorrect() {
        Mockito.when(userRepository.existsByUsername("Alex")).thenReturn(false);

        Assertions.assertThrows(BusinessException.class, () -> externalUserService.createUser(userCreateUpdateDtoForFailByEmail));
    }

    @Test
    public void updateUserMethodShouldReturnSuccessAndChangeEmail() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        Mockito.when(userRepository.existsByEmail("alexXx_cool@gmail.com")).thenReturn(false);
        Mockito.when(userRepository.save(user1)).thenReturn(user1);


        Mockito.when(userMapper.toUserResponseDTO(user1)).thenReturn(user1DtoWithChangedEmail);

        Assertions.assertEquals(user1DtoWithChangedEmail, externalUserService.updateUser(1L, userUpdateDtoForSuccessWithChangeEmail));
        Assertions.assertEquals(user1.getEmail(), user1DtoWithChangedEmail.getEmail());
    }

    @Test
    public void updateUserMethodShouldReturnSuccessAndChangeDisplayName() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        Mockito.when(userRepository.save(user1)).thenReturn(user1);
        Mockito.when(userMapper.toUserResponseDTO(user1)).thenReturn(user1DtoWithChangedDisplayName);

        Assertions.assertEquals(user1DtoWithChangedDisplayName, externalUserService.updateUser(1L, userUpdateDtoForSuccessWithChangeDisplayName));
        Assertions.assertEquals(user1.getDisplayName(), user1DtoWithChangedDisplayName.getDisplayName());
    }

    @Test
    public void updateUserMethodShouldReturnSuccessAndChangeInterests() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        Mockito.when(userRepository.save(user1)).thenReturn(user1);
        Mockito.when(userMapper.toUserResponseDTO(user1)).thenReturn(user1DtoWithChangedDisplayName);

        Assertions.assertEquals(user1DtoWithChangedInterests, externalUserService.updateUser(1L, userUpdateDtoForSuccessWithChangeInterests));
        Assertions.assertEquals(user1.getInterests(), user1DtoWithChangedInterests.getInterests());
    }

    @Test
    public void updateUserMethodShouldReturnSuccessAndChangeAll() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        Mockito.when(userRepository.existsByEmail("alexXx_cool@gmail.com")).thenReturn(false);
        Mockito.when(userRepository.save(user1)).thenReturn(user1);
        Mockito.when(userMapper.toUserResponseDTO(user1)).thenReturn(user1DtoWithChangedAll);

        Assertions.assertEquals(user1DtoWithChangedAll, externalUserService.updateUser(1L, userUpdateDtoForSuccessWithChangeAll));
    }

    @Test
    public void updateUserMethodShouldThrowResourceNotFoundExceptionBecauseUserNotFound() {
        Mockito.when(userRepository.findById(2L)).thenThrow(ResourceNotFoundException.class);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> externalUserService.updateUser(2L, userUpdateDtoForSuccessWithChangeEmail));
    }

    @Test
    public void updateUserMethodShouldThrowBusinessErrorBecauseEmailExists() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));
        Mockito.when(userRepository.existsByEmail("alex_cool@gmail.com")).thenReturn(true);

        Assertions.assertThrows(BusinessException.class, () -> externalUserService.updateUser(1L, userUpdateDtoForSuccessWithChangeDisplayName));
    }

    @Test
    public void updateUserMethodShouldThrowBusinessErrorBecauseEmailIsNotCorrect() {
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user1));

        Assertions.assertThrows(BusinessException.class, () -> externalUserService.updateUser(1L, userUpdateUpdateDtoForFailByEmail));
    }

    private void setUpForUpdateUserWithChangeEmail() {
        user1.setEmail("alexXx_cool@gmail.com");
    }

    private void setUpUser1Default() {
        user1.setEmail("alex_cool@gmail.com");
    }
}
