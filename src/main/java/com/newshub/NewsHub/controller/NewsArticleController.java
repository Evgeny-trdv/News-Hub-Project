package com.newshub.NewsHub.controller;

import com.newshub.NewsHub.dto.articleDTO.NewsArticleResponseDTO;
import com.newshub.NewsHub.service.NewsArticleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
@Tag(name = "Articles management", description = "APIs for managing news articles")
public class NewsArticleController {

    private final NewsArticleService newsArticleService;

    public NewsArticleController(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }

    @GetMapping("/article{articleId}")
    public NewsArticleResponseDTO getNewsArticle(@PathVariable Long articleId) {
        return newsArticleService.getNewsArticle(articleId);
    }

    @GetMapping()
    public List<NewsArticleResponseDTO> getNewsArticles(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        return newsArticleService.getAllNewsArticles(pageable).getContent();
    }

    @GetMapping("/by-category{category}")
    public List<NewsArticleResponseDTO> getNewsArticlesByCategory(@PathVariable String category, @PageableDefault(page = 0, size = 3) Pageable pageable) {
        return newsArticleService.getNewsArticlesByCategory(category, pageable).getContent();
    }

    @GetMapping("/by-source{sourceId}")
    public List<NewsArticleResponseDTO> getNewsArticlesBySource(@PathVariable Long sourceId, @PageableDefault(page = 0, size = 3) Pageable pageable) {
        return newsArticleService.getNewsArticlesBySource(sourceId, pageable).getContent();
    }
}