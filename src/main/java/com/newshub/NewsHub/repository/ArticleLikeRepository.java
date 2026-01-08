package com.newshub.NewsHub.repository;

import com.newshub.NewsHub.model.ArticleLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleLikeRepository extends JpaRepository<ArticleLike, Long> {

    @Query(value = "SELECT EXISTS(" +
            "SELECT 1 FROM article_likes " +
            "WHERE user_id = :userId AND article_id = :articleId" +
            ")", nativeQuery = true)
    public boolean existsByUserIdAndArticleId(@Param("userId") Long userId, @Param("articleId") Long articleId);

    @Query(value = "SELECT * FROM article_likes WHERE user_id =: userId", nativeQuery = true)
    public ArticleLike findByUserIdAndArticleId(Long userId, Long articleId);
}
