package com.newshub.NewsHub.repository;

import com.newshub.NewsHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с пользователями (User)
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    Optional<User> findById(Long id);

    void deleteById(Long id);

    boolean existsById(Long id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE user_status = 'DELETED'", nativeQuery = true)
    List<User> listUserForDeleteWithStatusDeleted();
}
