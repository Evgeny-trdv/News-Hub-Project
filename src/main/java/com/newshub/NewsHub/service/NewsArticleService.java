package com.newshub.NewsHub.service;

import com.newshub.NewsHub.dto.articleDTO.NewsArticleRequestDTO;
import com.newshub.NewsHub.dto.articleDTO.NewsArticleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NewsArticleService {

    public NewsArticleResponseDTO getNewsArticle(Long newsArticleId);

    public Page<NewsArticleResponseDTO> getAllNewsArticles(Pageable pageable);

    public Page<NewsArticleResponseDTO> getNewsArticlesByCategory(String category, Pageable pageable);

    public Page<NewsArticleResponseDTO> getNewsArticlesBySource(Long sourceId, Pageable pageable);

    public NewsArticleResponseDTO addNewsArticle(NewsArticleRequestDTO newsArticleRequestDTO);

    public NewsArticleResponseDTO updateNewsArticle(Long newsArticleId, NewsArticleRequestDTO newsArticleRequestDTO);

    public void deleteNewsArticle(Long newsArticleId);
}
