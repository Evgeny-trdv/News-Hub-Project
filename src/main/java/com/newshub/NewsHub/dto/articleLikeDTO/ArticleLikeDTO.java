package com.newshub.NewsHub.dto.articleLikeDTO;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * DTO для предоставления лайка статье
 */
public class ArticleLikeDTO {

    private Long userId;
    private Long articleId;

    public ArticleLikeDTO() {
    }

    public ArticleLikeDTO(Long userId, Long articleId) {
        this.userId = userId;
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleLikeDTO that = (ArticleLikeDTO) o;
        return Objects.equals(userId, that.userId) && Objects.equals(articleId, that.articleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, articleId);
    }

    @Override
    public String toString() {
        return "ArticleLikeDTO{" +
                "userId=" + userId +
                ", articleId=" + articleId +
                '}';
    }
}
