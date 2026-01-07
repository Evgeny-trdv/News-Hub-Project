package com.newshub.NewsHub.repository;

import com.newshub.NewsHub.model.NewsArticle;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Репозиторий для работы с новостными статьями
 */
@Repository
public interface NewsArticleRepository extends JpaRepository<NewsArticle, Long> {

    public boolean existsByExternalId(@NotNull String externalId);

    @Query(value = "SELECT * FROM news_articles WHERE category := category", nativeQuery = true)
    public Page<NewsArticle> findNewsArticlesByCategory(@NotNull @Param("category") String category,
                                                        @NotNull Pageable pageable);

    @Query(value = "SELECT * FROM news_articles WHERE category IN : categories ORDER BY published_at DESC", nativeQuery = true)
    public Page<NewsArticle> findNewsArticlesByCategories(@NotNull @Param("categories") Set<String> categories,
                                                        @NotNull Pageable pageable);

    @Query(value = "SELECT * FROM news_articles WHERE source_id := sourceId", nativeQuery = true)
    public Page<NewsArticle> findNewsArticlesBySource(@NotNull @Param("sourceId") Long sourceId, @NotNull Pageable pageable);

    @Query(value = "SELECT * FROM news_articles WHERE is_popular = TRUE", nativeQuery = true)
    public Page<NewsArticle> findNewsArticlesByIsPopularTrue(Pageable pageable);

    @Query(value = "SELECT * FROM news_article WHERE tags IN : tags ORDER BY published_at DESC", nativeQuery = true)
    public Page<NewsArticle> findByTags(@NotNull @Param("tags") Set<String> tags, @NotNull Pageable pageable);

}
