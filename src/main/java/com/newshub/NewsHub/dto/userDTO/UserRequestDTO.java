package com.newshub.NewsHub.dto.userDTO;

import com.newshub.NewsHub.model.Category;
import com.newshub.NewsHub.model.UserStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * DTO пользователя для создания или обновления пользователя (для передачи)
 */
public class UserRequestDTO {

    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Set<Category> categories;

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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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
