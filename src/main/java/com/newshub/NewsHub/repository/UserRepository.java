package com.newshub.NewsHub.repository;

import com.newshub.NewsHub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с пользователями (User)
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM users WHERE user_status = 'DELETED'", nativeQuery = true)
    List<User> listUserForDeleteWithStatusDeleted();
}
