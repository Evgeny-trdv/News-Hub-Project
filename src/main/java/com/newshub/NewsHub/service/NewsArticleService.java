package com.newshub.NewsHub.service;

import com.newshub.NewsHub.dto.articleDTO.NewsArticleRequestDTO;
import com.newshub.NewsHub.dto.articleDTO.NewsArticleResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NewsArticleService {

    public NewsArticleResponseDTO getNewsArticle(Long newsArticleId);

    public Page<NewsArticleResponseDTO> getNewsArticles(Pageable pageable);

    public NewsArticleResponseDTO addNewsArticle(NewsArticleRequestDTO newsArticleRequestDTO);

    public NewsArticleResponseDTO updateNewsArticle(Long newsArticleId, NewsArticleRequestDTO newsArticleRequestDTO);

    public void deleteNewsArticle(Long newsArticleId);
}
