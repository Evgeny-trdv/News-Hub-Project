package com.newshub.NewsHub.mapper;

import com.newshub.NewsHub.dto.articleLikeDTO.ArticleLikeDTO;
import com.newshub.NewsHub.model.ArticleLike;
import com.newshub.NewsHub.model.NewsArticle;
import com.newshub.NewsHub.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Преобразование сущности ArticleLike в DTO
 */
@Component
public class ArticleLikeMapper {

    public ArticleLikeDTO toArticleLikeDTO(ArticleLike like) {
        ArticleLikeDTO dto = new ArticleLikeDTO();
        dto.setUserId(like.getUser().getId());
        dto.setArticleId(like.getArticle().getId());
        return dto;
    }

    public ArticleLike toArticleLikeEntity(User user, NewsArticle article) {
        ArticleLike like = new ArticleLike();
        like.setUser(user);
        like.setArticle(article);
        like.setCreatedAt(LocalDateTime.now());
        like.setUpdatedAt(LocalDateTime.now());
        like.setLiked(true);
        return like;
    }
}
