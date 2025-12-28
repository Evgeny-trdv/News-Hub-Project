package com.newshub.NewsHub.mapper;


import com.newshub.NewsHub.dto.articleDTO.NewsArticleRequestDTO;
import com.newshub.NewsHub.dto.articleDTO.NewsArticleResponseDTO;
import com.newshub.NewsHub.model.NewsArticle;
import org.springframework.stereotype.Component;

/**
 * Преобразование сущности NewsArticle в DTO
 */
@Component
public class NewsArticleMapper {

    public NewsArticle toNewsArticleEntity(NewsArticleRequestDTO newsArticleRequestDTO) {
        NewsArticle newsArticle = new NewsArticle();

        newsArticle.setTitle(newsArticleRequestDTO.getTitle());
        newsArticle.setSummary(newsArticleRequestDTO.getSummary());
        newsArticle.setContent(newsArticleRequestDTO.getContent());
        newsArticle.setOriginalUrl(newsArticleRequestDTO.getOriginalUrl());
        newsArticle.setAuthor(newsArticleRequestDTO.getAuthor());
        newsArticle.setCategory(newsArticleRequestDTO.getCategory());

        if (newsArticleRequestDTO.getTags() != null) {
            newsArticle.setTags(newsArticleRequestDTO.getTags());
        }
        newsArticle.setPublishedAt(newsArticleRequestDTO.getPublishedAt());

        return newsArticle;
    }


    public NewsArticleResponseDTO toNewsArticleResponse(NewsArticle newsArticle) {
        NewsArticleResponseDTO newsArticleResponseDTO = new NewsArticleResponseDTO();

        newsArticleResponseDTO.setId(newsArticle.getId());
        newsArticleResponseDTO.setTitle(newsArticle.getTitle());
        newsArticleResponseDTO.setSummary(newsArticle.getSummary());
        newsArticleResponseDTO.setContent(newsArticle.getContent());
        newsArticleResponseDTO.setOriginalUrl(newsArticle.getOriginalUrl());
        newsArticleResponseDTO.setAuthor(newsArticle.getAuthor());
        newsArticleResponseDTO.setCategory(newsArticle.getCategory());

        if (newsArticle.getTags() != null) {
            newsArticleResponseDTO.setTags(newsArticle.getTags());
        }

        newsArticleResponseDTO.setPublishedAt(newsArticle.getPublishedAt());
        newsArticleResponseDTO.setAddedAt(newsArticle.getAddedAt());
        newsArticleResponseDTO.setLikesCount(newsArticle.getLikesCount());
        newsArticleResponseDTO.setViewsCount(newsArticle.getViewsCount());
        newsArticleResponseDTO.setFavoritesCount(newsArticle.getFavoritesCount());
        newsArticleResponseDTO.setPopular(newsArticle.getPopular());
        return newsArticleResponseDTO;
    }
}
