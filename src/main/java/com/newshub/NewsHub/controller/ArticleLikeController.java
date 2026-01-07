package com.newshub.NewsHub.controller;

import com.newshub.NewsHub.dto.articleLikeDTO.ArticleLikeDTO;
import com.newshub.NewsHub.service.ArticleLikeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/likes")
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;

    public ArticleLikeController(ArticleLikeService articleLikeService) {
        this.articleLikeService = articleLikeService;
    }

    @PostMapping("article/{articleId}")
    public ArticleLikeDTO addArticleLike(@PathVariable Long articleId, @RequestParam Long userId) {
        return articleLikeService.addArticleLike(articleId, userId);
    }
}
