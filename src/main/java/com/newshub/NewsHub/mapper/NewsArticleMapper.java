package com.newshub.NewsHub.mapper;


import com.newshub.NewsHub.dto.NewsArticleDTO;
import com.newshub.NewsHub.model.NewsArticle;
import org.springframework.stereotype.Component;

/**
 * Преобразование сущности NewsArticle в DTO
 */

@Component
public class NewsArticleMapper {

    public NewsArticleDTO toNewsArticleDTO(NewsArticle article) {
        if (article == null) {
            return null;
        }

        NewsArticleDTO newsArticleDTO = new NewsArticleDTO();

        newsArticleDTO.setId(article.getId());
        newsArticleDTO.setTitle(article.getTitle());
        newsArticleDTO.setContent(article.getContent());
        newsArticleDTO.setAuthor(article.getAuthor());
        newsArticleDTO.setCategory(article.getCategory());
        return newsArticleDTO;

    }
}
