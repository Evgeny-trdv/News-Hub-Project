package com.newshub.NewsHub.service.impl;

import com.newshub.NewsHub.dto.articleDTO.NewsArticleRequestDTO;
import com.newshub.NewsHub.dto.articleDTO.NewsArticleResponseDTO;
import com.newshub.NewsHub.exception.BusinessException;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис для работы с новостными статьями (NewsArticle)
 */
@Service
public class NewsArticleServiceImpl implements NewsArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsArticleServiceImpl.class);

    private final NewsArticleMapper newsArticleMapper;
    private final NewsArticleRepository newsArticleRepository;

    public NewsArticleServiceImpl(NewsArticleMapper newsArticleMapper, NewsArticleRepository newsArticleRepository) {
        this.newsArticleMapper = newsArticleMapper;
        this.newsArticleRepository = newsArticleRepository;
    }

    /**
     * Метод получения новостной статьи по id
     * @param newsArticleId id новостной статьи
     * @return DTO новостной статьи для получения ответа
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

    /**
     * Метод добавления новостной статьи
     * @param newsArticleRequestDTO DTO новостной статьи для создания/обновления
     * @return DTO новостной статьи для получения ответа
     */
    @Transactional
    @Override
    public NewsArticleResponseDTO addNewsArticle(NewsArticleRequestDTO newsArticleRequestDTO) {
        if (newsArticleRequestDTO == null) {
            throw new BusinessException("newsArticleRequestDTO is null");
        }
        if (newsArticleRepository.existsByTitle(newsArticleRequestDTO.getTitle())) {
            throw new BusinessException("Title already exists");
        }
        NewsArticle createdNewsArticle = newsArticleMapper.toNewsArticleEntity(newsArticleRequestDTO);
        newsArticleRepository.save(createdNewsArticle);

        LOGGER.info("News Article {} added successfully", createdNewsArticle.getTitle());

        return newsArticleMapper.toNewsArticleResponse(createdNewsArticle);
    }

    @Override
    public NewsArticleResponseDTO updateNewsArticle(Long newsArticleId, NewsArticleRequestDTO newsArticleRequestDTO) {
        return null;
    }

    @Override
    public void deleteNewsArticle(Long newsArticleId) {
        if (!newsArticleRepository.existsById(newsArticleId)) {
            throw new ResourceNotFoundException("NewsArticle", "id", newsArticleId);
        }

        LOGGER.info("News Article {} deleted successfully", newsArticleId);
        newsArticleRepository.deleteById(newsArticleId);
    }
}
