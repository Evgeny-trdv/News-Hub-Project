package com.newshub.NewsHub.mapper;


import com.newshub.NewsHub.dto.articleDTO.NewsArticleRequestDTO;
import com.newshub.NewsHub.dto.articleDTO.NewsArticleResponseDTO;
import com.newshub.NewsHub.model.NewsArticle;
import com.newshub.NewsHub.model.NewsSource;
import org.springframework.stereotype.Component;

/**
 * Преобразование сущности NewsArticle в DTO
 */
@Component
public class NewsArticleMapper {

    public NewsArticle toNewsArticleEntity(NewsArticleRequestDTO newsArticleRequestDTO, NewsSource source) {
        NewsArticle newsArticle = new NewsArticle();

        newsArticle.setTitle(newsArticleRequestDTO.getTitle());
        newsArticle.setSummary(newsArticleRequestDTO.getSummary());
        newsArticle.setContent(newsArticleRequestDTO.getContent());
        newsArticle.setOriginalUrl(newsArticleRequestDTO.getOriginalUrl());
        newsArticle.setAuthor(newsArticleRequestDTO.getAuthor());
        newsArticle.setCategory(newsArticleRequestDTO.getCategory());
        newsArticle.setSource(source);

        if (newsArticleRequestDTO.getTags() != null) {
            newsArticle.setTags(newsArticleRequestDTO.getTags());
        }
        newsArticle.setPublishedAt(newsArticleRequestDTO.getPublishedAt());
        newsArticle.setExternalId(newsArticleRequestDTO.getExternalId());
        newsArticle.setLanguage(newsArticleRequestDTO.getLanguage());

        return newsArticle;
    }


    public NewsArticleResponseDTO toNewsArticleResponseDTO(NewsArticle newsArticle) {
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
        if (newsArticle.getSource() != null) {
            newsArticleResponseDTO.setSourceId(newsArticle.getSource().getId());
            newsArticleResponseDTO.setSourceName(newsArticle.getSource().getName());
        }

        newsArticleResponseDTO.setPublishedAt(newsArticle.getPublishedAt());
        newsArticleResponseDTO.setAddedAt(newsArticle.getAddedAt());
        newsArticleResponseDTO.setLikesCount(newsArticle.getLikesCount());
        newsArticleResponseDTO.setViewsCount(newsArticle.getViewsCount());
        newsArticleResponseDTO.setFavoritesCount(newsArticle.getFavoritesCount());
        newsArticleResponseDTO.setPopular(newsArticle.getPopular());
        newsArticleResponseDTO.setLanguage(newsArticle.getLanguage());
        return newsArticleResponseDTO;
    }

    public void updateEntity(NewsArticle article, NewsArticleRequestDTO dto, NewsSource source) {
        if (dto.getTitle() != null) {
            article.setTitle(dto.getTitle());
        }
        if (dto.getSummary() != null) {
            article.setSummary(dto.getSummary());
        }
        if (dto.getContent() != null) {
            article.setContent(dto.getContent());
        }
        if (dto.getOriginalUrl() != null) {
            article.setOriginalUrl(dto.getOriginalUrl());
        }
        if (dto.getAuthor() != null) {
            article.setAuthor(dto.getAuthor());
        }
        if (dto.getCategory() != null) {
            article.setCategory(dto.getCategory());
        }
        if (dto.getTags() != null) {
            article.setTags(dto.getTags());
        }
        if (dto.getPublishedAt() != null) {
            article.setPublishedAt(dto.getPublishedAt());
        }
        if (source != null) {
            article.setSource(source);
        }
        if (dto.getExternalId() != null) {
            article.setExternalId(dto.getExternalId());
        }
        if (dto.getLanguage() != null) {
            article.setLanguage(dto.getLanguage());
        }
    }
}
