package com.newshub.NewsHub.controller;

import com.newshub.NewsHub.dto.userDTO.UserRequestDTO;
import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import com.newshub.NewsHub.service.UserService;
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

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user-id/{userId}")
    @Operation(summary = "Get the user by userId")
    public UserResponseDTO getUser(@PathVariable Long userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/user-name/{username}")
    @Operation(summary = "Get the user by username")
    public UserResponseDTO getUser(@PathVariable String username) {
        return userService.getUser(username);
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public List<UserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/all")
    @Operation(summary = "Get all users by Pageable ")
    public List<UserResponseDTO> getAllUsers(@PageableDefault(page = 0, size = 3, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return userService.getAllUsers(pageable).getContent();
    }

    @PostMapping
    @Operation(summary = "Create the new user")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.createUser(userRequestDTO);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete the user by userId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @DeleteMapping("/delete{userId}")
    @Operation(summary = "Delete the user by userId with ability to return user")
    public void deleteUserWithAbilityReturn(@PathVariable Long userId) {
        userService.deleteUserWithAbilityReturn(userId);
    }

    //Написать метод восстановления пользователя, который был удален временно (с userStatus = "DELETED")

    @PutMapping("/{userId}")
    @Operation(summary = "Update the user")
    public UserResponseDTO updateUser(@PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(userId, userRequestDTO);
    }

    @PostMapping("/{userId}/interest")
    @Operation(summary = "Add the interest to user")
    public UserResponseDTO addInterest(@PathVariable Long userId, @RequestParam String interest) {
        return userService.addInterestToUser(userId, interest);
    }

    @DeleteMapping("/{userId}/interests")
    @Operation(summary = "Remove the interest to user")
    public UserResponseDTO removeInterest(@PathVariable Long userId, @RequestParam String interest) {
        return userService.removeInterestToUser(userId, interest);
    }

    @GetMapping("/{userId}/interests")
    @Operation(summary = "Get all interests user")
    public Set<String> getInterests(@PathVariable Long userId) {
        return userService.getUserInterests(userId);
    }

}
