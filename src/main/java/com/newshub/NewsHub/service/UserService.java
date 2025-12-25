package com.newshub.NewsHub.service;

import com.newshub.NewsHub.dto.userDTO.UserRequestDTO;
import com.newshub.NewsHub.dto.userDTO.UserResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

public interface UserService {

    public UserResponseDTO getUser(Long id);
    public UserResponseDTO getUser(String username);

    public List<UserResponseDTO> getAllUsers();
    public Page<UserResponseDTO> getAllUsers(Pageable pageable);

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    public UserResponseDTO updateUser(Long userId, UserRequestDTO userRequestDTO);

    public void deleteUser(Long id);
    public void deleteUserWithAbilityReturn(Long userId);

    public UserResponseDTO addInterestToUser(Long id, String interest);
    public UserResponseDTO removeInterestToUser(Long id, String interest);
    public Set<String> getUserInterests(Long id);

}
