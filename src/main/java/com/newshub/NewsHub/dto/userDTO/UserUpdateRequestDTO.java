package com.newshub.NewsHub.dto.userDTO;

import java.util.Objects;
import java.util.Set;

/**
 * DTO для обновления данных пользователя (изменения со стороны пользователя)
 */
public class UserUpdateRequestDTO {

    private String displayName;
    private String email;
    private Set<String> interests;

    public UserUpdateRequestDTO(String displayName, String email) {
        this.displayName = displayName;
        this.email = email;
    }

    public UserUpdateRequestDTO() {
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        UserUpdateRequestDTO that = (UserUpdateRequestDTO) o;
        return Objects.equals(displayName, that.displayName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(displayName);
    }

    @Override
    public String toString() {
        return "UserUpdateRequestDTO{" +
                "username='" + displayName + '\'' +
                ", email='" + email + '\'' +
                ", interests=" + interests +
                '}';
    }
}
