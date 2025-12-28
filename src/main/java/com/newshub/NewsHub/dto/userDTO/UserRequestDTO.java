package com.newshub.NewsHub.dto.userDTO;

import java.util.Objects;
import java.util.Set;

/**
 * DTO пользователя для создания или обновления пользователя (для передачи)
 */
public class UserRequestDTO {

    private String username;
    private String email;
    private String password;
    private Set<String> interests;

    public UserRequestDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserRequestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getInterests() {
        return interests;
    }

    public void setInterests(Set<String> interests) {
        this.interests = interests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRequestDTO that = (UserRequestDTO) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public String toString() {
        return "UserRequestDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
