package com.newshub.NewsHub.controller;

import com.newshub.NewsHub.dto.userDTO.UserCreateUpdateRequestDto;
import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import com.newshub.NewsHub.service.AuthService;
import com.newshub.NewsHub.service.UserToAdminService;
import com.newshub.NewsHub.service.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User management", description = "APIs for managing users")
public class UserController {

    private final UserToAdminService userToAdminService;

    public UserController(UserToAdminService userToAdminService) {
        this.userToAdminService = userToAdminService;
    }

    @GetMapping("/user-id/{userId}")
    @Operation(summary = "Get the user by userId")
    public UserResponseDTO getUser(@PathVariable Long userId) {
        return userToAdminService.getUser(userId);
    }

    @GetMapping("/user-name/{username}")
    @Operation(summary = "Get the user by username")
    public UserResponseDTO getUser(@PathVariable String username) {
        return userToAdminService.getUser(username);
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public List<UserResponseDTO> getAllUsers() {
        return userToAdminService.getAllUsers();
    }

    @GetMapping("/all")
    @Operation(summary = "Get all users by Pageable ")
    public List<UserResponseDTO> getAllUsers(@PageableDefault(page = 0, size = 3, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return userToAdminService.getAllUsers(pageable).getContent();
    }

    @PostMapping
    @Operation(summary = "Create the new user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody UserCreateUpdateRequestDto userCreateDto) {
        return userToAdminService.createUser(userCreateDto);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete the user by userId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userToAdminService.deleteUser(userId);
    }

    @DeleteMapping("/delete{userId}")
    @Operation(summary = "Delete the user by userId with ability to return user")
    public void deleteUserWithAbilityReturn(@PathVariable Long userId) {
        userToAdminService.deleteUserWithAbilityReturn(userId);
    }

    //Написать метод восстановления пользователя, который был удален временно (с userStatus = "DELETED")

    @PutMapping("/{userId}")
    @Operation(summary = "Update the user")
    public UserResponseDTO updateUser(@PathVariable Long userId, @RequestBody UserCreateUpdateRequestDto userUpdateRequestDTO) {
        return userToAdminService.updateUser(userId, userUpdateRequestDTO);
    }

    @PostMapping("/{userId}/interest")
    @Operation(summary = "Add the interest to user")
    public UserResponseDTO addInterest(@PathVariable Long userId, @RequestParam String interest) {
        return userToAdminService.addInterestToUser(userId, interest);
    }

    @DeleteMapping("/{userId}/interests")
    @Operation(summary = "Remove the interest to user")
    public UserResponseDTO removeInterest(@PathVariable Long userId, @RequestParam String interest) {
        return userToAdminService.removeInterestToUser(userId, interest);
    }

    @GetMapping("/{userId}/interests")
    @Operation(summary = "Get all interests user")
    public Set<String> getInterests(@PathVariable Long userId) {
        return userToAdminService.getUserInterests(userId);
    }

}
