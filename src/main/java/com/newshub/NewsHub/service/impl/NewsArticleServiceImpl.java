package com.newshub.NewsHub.service.impl;

import com.newshub.NewsHub.dto.articleDTO.NewsArticleResponseDTO;
import com.newshub.NewsHub.exception.ResourceNotFoundException;
import com.newshub.NewsHub.mapper.NewsArticleMapper;
import com.newshub.NewsHub.model.NewsArticle;
import com.newshub.NewsHub.repository.NewsArticleRepository;
import com.newshub.NewsHub.service.NewsArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с новостными статьями (NewsArticle)
 */
@Service
public class NewsArticleServiceImpl implements NewsArticleService {

    private final Logger logger = LoggerFactory.getLogger(NewsArticleServiceImpl.class);

    private final NewsArticleMapper newsArticleMapper;
    private final NewsArticleRepository newsArticleRepository;

    public NewsArticleServiceImpl(NewsArticleMapper newsArticleMapper, NewsArticleRepository newsArticleRepository) {
        this.newsArticleMapper = newsArticleMapper;
        this.newsArticleRepository = newsArticleRepository;
    }

    /**
     * Метод получения новостной статьи по id
     * @param newsArticleId id новостной статьи
     * @return ответ DTO новостной статьи для получения ответа
     */
    @Override
    public NewsArticleResponseDTO getNewsArticle(Long newsArticleId) {

        NewsArticle findedNewsArticle = newsArticleRepository.findById(newsArticleId)
                .orElseThrow(() -> new ResourceNotFoundException("NewsArticle", "id", newsArticleId));

        return newsArticleMapper.toNewsArticleResponse(findedNewsArticle);
    }

    /**
     * Метод получения всех новостных статей через пагинацию (по странично)
     * @param pageable объект постраничного запроса
     * @return список всех DTO новостных статей для получения ответа
     */
    @Override
    public Page<NewsArticleResponseDTO> getNewsArticles(Pageable pageable) {
        Page<NewsArticle> findedNewsArticles = newsArticleRepository.findAll(pageable);
        return findedNewsArticles.map(newsArticleMapper::toNewsArticleResponse);
    }
}
