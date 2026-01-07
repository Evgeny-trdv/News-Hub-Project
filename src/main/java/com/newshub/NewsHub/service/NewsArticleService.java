package com.newshub.NewsHub.service;

import com.newshub.NewsHub.dto.articleDTO.NewsArticleRequestDTO;
import com.newshub.NewsHub.dto.articleDTO.NewsArticleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface NewsArticleService {

    public NewsArticleResponseDTO getNewsArticleById(Long newsArticleId);

    public Page<NewsArticleResponseDTO> getAllNewsArticles(Pageable pageable);

    public Page<NewsArticleResponseDTO> getNewsArticlesByCategory(String category, Pageable pageable);

    public Page<NewsArticleResponseDTO> getNewsArticlesBySource(Long sourceId, Pageable pageable);

    public Page<NewsArticleResponseDTO> getNewsArticlesByIsPopular(Pageable pageable);

    public NewsArticleResponseDTO addNewsArticle(NewsArticleRequestDTO newsArticleRequestDTO);

    public NewsArticleResponseDTO updateNewsArticle(Long newsArticleId, NewsArticleRequestDTO newsArticleRequestDTO);

    public void deleteNewsArticle(Long newsArticleId);

    public Page<NewsArticleResponseDTO> getUserFeed(Long userId, Pageable pageable);

    public NewsArticleResponseDTO addToFavorites(Long newsArticleId, Long userId);

    public void removeFromFavorites(Long newsArticleId, Long userId);

    public void markAsRead(Long newsArticleId, Long userId);

    public Page<NewsArticleResponseDTO> getFreshNewsArticles(Pageable pageable);

    public Page<NewsArticleResponseDTO> getNewsArticleByTags(Set<String> tags, Pageable pageable);
}
