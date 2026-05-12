package com.newshub.NewsHub.dto.userDTO;

import lombok.Data;

import java.util.Objects;
import java.util.Set;

/**
 * DTO для создания/обновления данных пользователя (изменения со стороны администратора)
 */
@Data
public class UserCreateUpdateRequestDto {

    private String username;
    private String email;
    private String displayName;
    private String password;
    private Set<String> interests;

    public UserCreateUpdateRequestDto() {
    }

    public UserCreateUpdateRequestDto(String username, String email, String displayName, String password, Set<String> interests) {
        this.username = username;
        this.email = email;
        this.displayName = displayName;
        this.password = password;
        this.interests = interests;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
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
        UserCreateUpdateRequestDto that = (UserCreateUpdateRequestDto) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public String toString() {
        return "UserCreateUpdateRequestDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", interests=" + interests +
                '}';
    }
}
