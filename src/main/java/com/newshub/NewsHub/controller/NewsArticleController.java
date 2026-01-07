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
import java.util.Set;

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

    @GetMapping("/category/{category}")
    @Operation(summary = "Get all news article by category")
    public List<NewsArticleResponseDTO> getNewsArticlesByCategory(@PathVariable String category, @PageableDefault(page = 0, size = 3) Pageable pageable) {
        return newsArticleService.getNewsArticlesByCategory(category, pageable).getContent();
    }

    @GetMapping("/source/{sourceId}")
    @Operation(summary = "Get all news article by source")
    public List<NewsArticleResponseDTO> getNewsArticlesBySource(@PathVariable Long sourceId, @PageableDefault(page = 0, size = 3) Pageable pageable) {
        return newsArticleService.getNewsArticlesBySource(sourceId, pageable).getContent();
    }

    @GetMapping("/tags")
    @Operation(summary = "Get all news article by tags")
    public List<NewsArticleResponseDTO> getNewsArticlesByTags(@RequestParam Set<String> tags, @PageableDefault(page = 0, size = 3) Pageable pageable) {
        return newsArticleService.getNewsArticleByTags(tags, pageable).getContent();
    }

    @GetMapping("/popular")
    @Operation(summary = "Get all popular news article")
    public List<NewsArticleResponseDTO> getNewsArticlesPopular(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        return newsArticleService.getNewsArticlesByIsPopular(pageable).getContent();
    }

    @GetMapping("/fresh")
    @Operation(summary = "Get all fresh news article")
    public List<NewsArticleResponseDTO> getNewsArticlesFresh(@PageableDefault(page = 0, size = 3) Pageable pageable) {
        return newsArticleService.getFreshNewsArticles(pageable).getContent();
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

    @GetMapping("/feed/{userId}")
    @Operation(summary = "Get news feed for user")
    public List<NewsArticleResponseDTO> getNewsFeedForUser(@PathVariable Long userId, @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return newsArticleService.getUserFeed(userId, pageable).getContent();
    }

    @PostMapping("/{articleId}/favorites")
    @Operation(summary = "Add article to favorites")
    @ResponseStatus(HttpStatus.CREATED)
    public NewsArticleResponseDTO addNewsArticleToFavorites(@PathVariable Long articleId, @RequestParam Long userId) {
        return newsArticleService.addToFavorites(articleId, userId);
    }

    @DeleteMapping("/{articleId}/favorites")
    @Operation(summary = "Remove article from favorites")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeNewsArticleFromFavorites(@PathVariable Long articleId, @RequestParam Long userId) {
        newsArticleService.removeFromFavorites(articleId, userId);
    }

    @GetMapping("/{articleId}/read")
    @Operation(summary = "Reading article by user")
    public void readNewsArticle(@PathVariable Long articleId, @RequestParam Long userId) {
        newsArticleService.markAsRead(articleId, userId);
    }

}