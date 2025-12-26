package com.newshub.NewsHub.repository;

import com.newshub.NewsHub.model.NewsArticle;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с новостными статьями
 */
@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {

}
