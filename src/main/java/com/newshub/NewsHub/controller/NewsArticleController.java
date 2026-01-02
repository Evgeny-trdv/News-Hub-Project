package com.newshub.NewsHub.controller;

import com.newshub.NewsHub.dto.articleDTO.NewsArticleRequestDTO;
import com.newshub.NewsHub.dto.articleDTO.NewsArticleResponseDTO;
import com.newshub.NewsHub.service.NewsArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "Get news article by id")
    public NewsArticleResponseDTO getNewsArticle(@PathVariable Long articleId) {
        return newsArticleService.getNewsArticleById(articleId);
    }

    @GetMapping()
    @Operation(summary = "Get all news article")
    public List<NewsArticleResponseDTO> getNewsArticles(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        return newsArticleService.getAllNewsArticles(pageable).getContent();
    }

    @GetMapping("/by-category{category}")
    @Operation(summary = "Get all news article by category")
    public List<NewsArticleResponseDTO> getNewsArticlesByCategory(@PathVariable String category, @PageableDefault(page = 0, size = 3) Pageable pageable) {
        return newsArticleService.getNewsArticlesByCategory(category, pageable).getContent();
    }

    @GetMapping("/by-source{sourceId}")
    @Operation(summary = "Get all news article by source")
    public List<NewsArticleResponseDTO> getNewsArticlesBySource(@PathVariable Long sourceId, @PageableDefault(page = 0, size = 3) Pageable pageable) {
        return newsArticleService.getNewsArticlesBySource(sourceId, pageable).getContent();
    }

    @GetMapping("/is-popular")
    @Operation(summary = "Get all news article are popular")
    public List<NewsArticleResponseDTO> getNewsArticlesBySource(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        return newsArticleService.getNewsArticlesByIsPopular(pageable).getContent();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create news article")
    public NewsArticleResponseDTO createNewsArticle(@RequestBody NewsArticleRequestDTO newsArticleRequestDTO) {
        return newsArticleService.addNewsArticle(newsArticleRequestDTO);
    }

    @PutMapping("/{articleId}")
    @Operation(summary = "Update news article")
    public NewsArticleResponseDTO updateNewsArticle(@PathVariable Long articleId ,@RequestBody NewsArticleRequestDTO newsArticleRequestDTO) {
        return newsArticleService.updateNewsArticle(articleId, newsArticleRequestDTO);
    }

    @DeleteMapping("/{articleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete news article")
    public void deleteNewsArticle(@PathVariable Long articleId) {
        newsArticleService.deleteNewsArticle(articleId);
    }
}