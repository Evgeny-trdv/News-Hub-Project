package com.newshub.NewsHub.mapper;


import com.newshub.NewsHub.dto.articleDTO.NewsArticleResponseDTO;
import com.newshub.NewsHub.model.NewsArticle;
import org.springframework.stereotype.Component;

/**
 * Преобразование сущности NewsArticle в DTO
 */

@Component
public class NewsArticleMapper {

    public NewsArticleResponseDTO toNewsArticleDTO(NewsArticle article) {
        if (article == null) {
            return null;
        }

        NewsArticleResponseDTO newsArticleResponseDTO = new NewsArticleResponseDTO();

        newsArticleResponseDTO.setId(article.getId());
        newsArticleResponseDTO.setTitle(article.getTitle());
        newsArticleResponseDTO.setContent(article.getContent());
        newsArticleResponseDTO.setAuthor(article.getAuthor());
        newsArticleResponseDTO.setCategory(article.getCategory());
        return newsArticleResponseDTO;

    }
}
