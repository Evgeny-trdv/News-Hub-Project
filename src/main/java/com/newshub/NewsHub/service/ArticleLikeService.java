package com.newshub.NewsHub.service;

import com.newshub.NewsHub.dto.articleLikeDTO.ArticleLikeDTO;

public interface ArticleLikeService {

    public ArticleLikeDTO addArticleLike(Long userId, Long articleId);

    public void removeArticleLike(Long userId, Long articleId);

    public ArticleLikeDTO getArticleLike(String articleId);

    public int getCountLikeByUser(Long userId);

    public int getCountLikeByArticle(Long articleId);

    public void toggleLike(Long userId, Long articleId);
}
