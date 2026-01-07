package com.newshub.NewsHub.service.impl;

import com.newshub.NewsHub.dto.articleLikeDTO.ArticleLikeDTO;
import com.newshub.NewsHub.exception.ResourceNotFoundException;
import com.newshub.NewsHub.mapper.ArticleLikeMapper;
import com.newshub.NewsHub.model.ArticleLike;
import com.newshub.NewsHub.model.NewsArticle;
import com.newshub.NewsHub.model.User;
import com.newshub.NewsHub.repository.ArticleLikeRepository;
import com.newshub.NewsHub.repository.NewsArticleRepository;
import com.newshub.NewsHub.repository.UserRepository;
import com.newshub.NewsHub.service.ArticleLikeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ArticleLikeServiceImpl implements ArticleLikeService {

    private final ArticleLikeRepository articleLikeRepository;
    private final UserRepository userRepository;
    private final NewsArticleRepository newsArticleRepository;
    private final ArticleLikeMapper articleLikeMapper;

    public ArticleLikeServiceImpl(ArticleLikeRepository articleLikeRepository, UserRepository userRepository, NewsArticleRepository newsArticleRepository, ArticleLikeMapper articleLikeMapper) {
        this.articleLikeRepository = articleLikeRepository;
        this.userRepository = userRepository;
        this.newsArticleRepository = newsArticleRepository;
        this.articleLikeMapper = articleLikeMapper;
    }

    @Override
    public ArticleLikeDTO addArticleLike(Long userId, Long articleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        NewsArticle newsArticle = newsArticleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("NewsArticle", "id", articleId));

        ArticleLike like = articleLikeMapper.toArticleLikeEntity(user, newsArticle);
        articleLikeRepository.save(like);
        return articleLikeMapper.toArticleLikeDTO(like);
    }

    @Override
    public void removeArticleLike(Long userId, Long articleId) {

    }

    @Override
    public ArticleLikeDTO getArticleLike(String articleId) {
        return null;
    }

    @Override
    public int getCountLikeByUser(Long userId) {
        return 0;
    }

    @Override
    public int getCountLikeByArticle(Long articleId) {
        return 0;
    }

    @Override
    public void toggleLike(Long userId, Long articleId) {

    }
}
