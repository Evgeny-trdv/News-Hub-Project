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
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public ArticleLikeDTO addArticleLike(Long userId, Long articleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        NewsArticle newsArticle = newsArticleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("NewsArticle", "id", articleId));

        if (articleLikeRepository.existsByUserIdAndArticleId(userId, articleId)) {
            ArticleLike existingLike = articleLikeRepository.getByUserIdAndArticleId(userId, articleId);
            if (!existingLike.getLiked()) {
                existingLike.restore();
                articleLikeRepository.save(existingLike);
                return articleLikeMapper.toArticleLikeDTO(existingLike);
            }
        }
        ArticleLike like = articleLikeMapper.toArticleLikeEntity(user, newsArticle);
        user.addLikeToNewsArticle(newsArticle, like);
        newsArticle.addArticleLikeByUser(user, like);

        articleLikeRepository.save(like);
        userRepository.save(user); //попробоват без него, возможно автоматическое сохранение в основных таблицах?
        newsArticleRepository.save(newsArticle); //попробоват без него, возможно автоматическое сохранение в основных таблицах?

        return articleLikeMapper.toArticleLikeDTO(like);
    }

    @Override
    public void removeArticleLike(Long userId, Long articleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        NewsArticle newsArticle = newsArticleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("NewsArticle", "id", articleId));

        if (articleLikeRepository.existsByUserIdAndArticleId(userId, articleId)) {
            ArticleLike existingLike = articleLikeRepository.getByUserIdAndArticleId(userId, articleId);
            existingLike.cancel();
            newsArticle.removeArticleLikeByUser(user);

            articleLikeRepository.save(existingLike);
            newsArticleRepository.save(newsArticle);
        }
    }

    @Override
    public ArticleLikeDTO getArticleLike(Long userId, Long articleId) {
        validUserIdElseThrow(userId);
        validArticleIdElseThrow(articleId);
        ArticleLike foundArticleLike = articleLikeRepository.findByUserIdAndArticleId(userId, articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article like is not found; " + "userId: " + userId + ", articleId: " + articleId));
        return articleLikeMapper.toArticleLikeDTO(foundArticleLike);
    }

    @Override
    public int getCountLikeByUser(Long userId) {
        validUserIdElseThrow(userId);
        return articleLikeRepository.getCountByUserId(userId);
    }

    @Override
    public int getCountLikeByArticle(Long articleId) {
        validArticleIdElseThrow(articleId);
        return articleLikeRepository.getCountByArticleId(articleId);
    }

    @Override
    public boolean toggleLike(Long userId, Long articleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        NewsArticle newsArticle = newsArticleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("NewsArticle", "id", articleId));
        ArticleLike foundArticleLike = articleLikeRepository.findByUserIdAndArticleId(userId, articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article like is not found; " + "userId: " + userId + ", articleId: " + articleId));

        if (!foundArticleLike.getLiked()) {
            foundArticleLike.restore();
            newsArticle.addArticleLikeByUser(user, foundArticleLike);

            articleLikeRepository.save(foundArticleLike);
            newsArticleRepository.save(newsArticle);
            return true;
        } else {
            foundArticleLike.cancel();
            newsArticle.removeArticleLikeByUser(user);

            articleLikeRepository.save(foundArticleLike);
            newsArticleRepository.save(newsArticle);
            return false;
        }
    }

    private void validUserIdElseThrow(Long userId) throws ResourceNotFoundException {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", userId);
        }
    }

    private void validArticleIdElseThrow(Long articleId) throws ResourceNotFoundException {
        if (!newsArticleRepository.existsById(articleId)) {
            throw new ResourceNotFoundException("NewsArticle", "id", articleId);
        }
    }
}
