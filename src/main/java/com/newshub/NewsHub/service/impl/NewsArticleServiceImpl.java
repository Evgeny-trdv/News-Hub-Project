package com.newshub.NewsHub.service.impl;

import com.newshub.NewsHub.dto.articleDTO.NewsArticleRequestDTO;
import com.newshub.NewsHub.dto.articleDTO.NewsArticleResponseDTO;
import com.newshub.NewsHub.dto.feedDTO.FeedDto;
import com.newshub.NewsHub.dto.feedDTO.FeedRequestDTO;
import com.newshub.NewsHub.exception.BusinessException;
import com.newshub.NewsHub.exception.ResourceNotFoundException;
import com.newshub.NewsHub.mapper.NewsArticleMapper;
import com.newshub.NewsHub.model.NewsArticle;
import com.newshub.NewsHub.model.NewsSource;
import com.newshub.NewsHub.model.User;
import com.newshub.NewsHub.repository.NewsArticleRepository;
import com.newshub.NewsHub.repository.NewsSourceRepository;
import com.newshub.NewsHub.repository.UserRepository;
import com.newshub.NewsHub.service.NewsArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сервис для работы с новостными статьями (NewsArticle)
 */
@Service
public class NewsArticleServiceImpl implements NewsArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsArticleServiceImpl.class);

    private final NewsArticleMapper newsArticleMapper;
    private final NewsArticleRepository newsArticleRepository;
    private final NewsSourceRepository newsSourceRepository;
    private final UserRepository userRepository;

    public NewsArticleServiceImpl(NewsArticleMapper newsArticleMapper, NewsArticleRepository newsArticleRepository, NewsSourceRepository newsSourceRepository, UserRepository userRepository) {
        this.newsArticleMapper = newsArticleMapper;
        this.newsArticleRepository = newsArticleRepository;
        this.newsSourceRepository = newsSourceRepository;
        this.userRepository = userRepository;
    }

    /**
     * Метод получения новостной статьи по id
     * @param newsArticleId id новостной статьи
     * @return DTO новостной статьи для получения ответа
     */
    @Transactional
    @Override
    public NewsArticleResponseDTO getNewsArticleById(Long newsArticleId) {
        NewsArticle findedNewsArticle = newsArticleRepository.findById(newsArticleId)
                .orElseThrow(() -> new ResourceNotFoundException("NewsArticle", "id", newsArticleId));

        findedNewsArticle.incrementViewsCount();
        newsArticleRepository.save(findedNewsArticle);

        return newsArticleMapper.toNewsArticleResponseDTO(findedNewsArticle);
    }

    /**
     * Метод получения всех новостных статей через пагинацию (по странично)
     * @param pageable объект постраничного запроса
     * @return список всех DTO новостных статей для получения ответа
     */
    @Override
    public Page<NewsArticleResponseDTO> getAllNewsArticles(Pageable pageable) {
        Page<NewsArticle> foundNewsArticles = newsArticleRepository.findAll(pageable);
        return foundNewsArticles.map(newsArticleMapper::toNewsArticleResponseDTO);
    }

    /**
     * Метод получения всех новостных статей определённой категории (по странично)
     * @param category категория новостной статьи
     * @param pageable объект постраничного запроса
     * @return список всех DTO новостных статей по категории для получения ответа
     */
    @Override
    public Page<NewsArticleResponseDTO> getNewsArticlesByCategory(String category, Pageable pageable) {
        Page<NewsArticle> foundNewsArticlesByCategory = newsArticleRepository.findNewsArticlesByCategory(category, pageable);
        return foundNewsArticlesByCategory.map(newsArticleMapper::toNewsArticleResponseDTO);
    }

    /**
     * Метод получения всех новостных статей определённого источника новостей (по странично)
     * @param sourceId id источника новостей
     * @param pageable объект постраничного запроса
     * @return список всех DTO новостных статей по источнику новостей для получения ответа
     */
    @Override
    public Page<NewsArticleResponseDTO> getNewsArticlesBySource(Long sourceId, Pageable pageable) {
        Page<NewsArticle> foundNewsArticlesBySource = newsArticleRepository.findNewsArticlesBySource(sourceId, pageable);
        return foundNewsArticlesBySource.map(newsArticleMapper::toNewsArticleResponseDTO);
    }

    /**
     * Метод получения всех популярных новостных статей (по станично)
     * @param pageable объект постраничного запроса
     * @return список всех DTO новостных статей для получения ответа, популярных
     */
    @Override
    public Page<NewsArticleResponseDTO> getNewsArticlesByIsPopular(Pageable pageable) {
        Page<NewsArticle> foundNewsArticlesByIsPopular = newsArticleRepository.findNewsArticlesByIsPopularTrue(pageable);
        return foundNewsArticlesByIsPopular.map(newsArticleMapper::toNewsArticleResponseDTO);
    }

    /**
     * Метод добавления новостной статьи
     * @param newsArticleRequestDTO DTO новостной статьи для создания/обновления
     * @return DTO созданной новостной статьи для получения ответа
     */
    @Transactional
    @Override
    public NewsArticleResponseDTO addNewsArticle(NewsArticleRequestDTO newsArticleRequestDTO) {
        if (newsArticleRequestDTO == null) {
            throw new BusinessException("newsArticleRequestDTO is null");
        }

        NewsSource source = newsSourceRepository.findById(newsArticleRequestDTO.getSourceId())
                .orElseThrow(() -> new ResourceNotFoundException("NewsSource", "id", newsArticleRequestDTO.getSourceId()));

        if (newsArticleRequestDTO.getExternalId() != null &&
                newsArticleRepository.existsByExternalId(newsArticleRequestDTO.getExternalId())) {
            throw new BusinessException("Article with externalId already exists: " + newsArticleRequestDTO.getExternalId());
        }

        NewsArticle createdNewsArticle = newsArticleMapper.toNewsArticleEntity(newsArticleRequestDTO, source);
        newsArticleRepository.save(createdNewsArticle);

        LOGGER.info("News Article {} added successfully", createdNewsArticle.getTitle());

        return newsArticleMapper.toNewsArticleResponseDTO(createdNewsArticle);
    }

    /**
     * Метод обновления информации новостной статьи
     * @param newsArticleId id новостной статьи
     * @param newsArticleRequestDTO DTO новостной статьи для создания/обновления
     * @return DTO обновленной новостной статьи для получения ответа
     */
    @Override
    @Transactional
    public NewsArticleResponseDTO updateNewsArticle(Long newsArticleId, NewsArticleRequestDTO newsArticleRequestDTO) {
        NewsArticle foundNewsArticle = newsArticleRepository.findById(newsArticleId)
                .orElseThrow(() -> new ResourceNotFoundException("NewsArticle", "id", newsArticleId));

        NewsSource source = null;
        if (newsArticleRequestDTO.getSourceId() != null) {
            LOGGER.info("NewsArticle with id {} have NewsSource - id {}", newsArticleId, newsArticleRequestDTO.getSourceId());
            source = newsSourceRepository.findById(newsArticleRequestDTO.getSourceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Source", "id", newsArticleRequestDTO.getSourceId()));
        }
        newsArticleMapper.updateEntity(foundNewsArticle, newsArticleRequestDTO, source);
        NewsArticle updatedArticle = newsArticleRepository.save(foundNewsArticle);

        LOGGER.info("News Article {} updated successfully", updatedArticle.getId());
        return newsArticleMapper.toNewsArticleResponseDTO(updatedArticle);
    }

    /**
     * Метод удаления новостной статьи
     * @param newsArticleId id новостной статьи
     */
    @Override
    @Transactional
    public void deleteNewsArticle(Long newsArticleId) {
        if (!newsArticleRepository.existsById(newsArticleId)) {
            throw new ResourceNotFoundException("NewsArticle", "id", newsArticleId);
        }
        LOGGER.info("News Article {} deleted successfully", newsArticleId);
        newsArticleRepository.deleteById(newsArticleId);
    }

    /**
     * метод добавления статьи в избранное
     * @param newsArticleId id новостной статьи
     * @param userId id пользователя
     * @return DTO новостной статьи для получения ответа
     */
    @Override
    @Transactional
    public NewsArticleResponseDTO addToFavorites(Long newsArticleId, Long userId) {
        NewsArticle articleForAddingFavorites = newsArticleRepository.findById(newsArticleId)
                .orElseThrow(() -> new ResourceNotFoundException("NewsArticle", "id", newsArticleId));

        User userWhoAdding = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (userWhoAdding.hasInFavorites(articleForAddingFavorites)) {
            throw new BusinessException("User already has favorites");
        }

        articleForAddingFavorites.getFavoritedBy().add(userWhoAdding);
        articleForAddingFavorites.incrementFavoritesCount();
        userWhoAdding.getFavoriteArticles().add(articleForAddingFavorites);

        newsArticleRepository.save(articleForAddingFavorites);
        userRepository.save(userWhoAdding);

        return newsArticleMapper.toNewsArticleResponseDTO(articleForAddingFavorites);
    }

    /**
     * метод удаления статьи из избранного
     * @param newsArticleId id новостной статьи
     * @param userId id пользователя
     */
    @Override
    @Transactional
    public void removeFromFavorites(Long newsArticleId, Long userId) {
        NewsArticle articleForRemovingFavorites = newsArticleRepository.findById(newsArticleId)
                .orElseThrow(() -> new ResourceNotFoundException("NewsArticle", "id", newsArticleId));

        User userWhoRemoving = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        if (!userWhoRemoving.hasInFavorites(articleForRemovingFavorites)) {
            throw new BusinessException("User does not have favorites");
        }

        if (userWhoRemoving.hasInFavorites(articleForRemovingFavorites)) {
            articleForRemovingFavorites.getFavoritedBy().remove(userWhoRemoving);
            articleForRemovingFavorites.decrementFavoritesCount();
            userWhoRemoving.getFavoriteArticles().remove(articleForRemovingFavorites);

            newsArticleRepository.save(articleForRemovingFavorites);
            userRepository.save(userWhoRemoving);
        }
    }

    /**
     * метод для отметки о прочтении пользователем новостной статьи
     * @param newsArticleId id новостной статьи
     * @param userId id пользователя
     */
    @Override
    @Transactional
    public void markAsRead(Long newsArticleId, Long userId) {
        NewsArticle articleForRemovingFavorites = newsArticleRepository.findById(newsArticleId)
                .orElseThrow(() -> new ResourceNotFoundException("NewsArticle", "id", newsArticleId));

        User userWhoRead = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        userWhoRead.markIsRead(articleForRemovingFavorites);

        userRepository.save(userWhoRead);
        newsArticleRepository.save(articleForRemovingFavorites);
    }

    /**
     * Метод формирования ленты новостей на основе интересов пользователя
     * @param userId id пользователя
     * @param pageable объект постраничного запроса
     * @return список всех DTO новостей по странично
     */
    @Override
    @Transactional
    public Page<NewsArticleResponseDTO> getUserFeed(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        Set<String> interestsUser = Set.copyOf(user.getInterests());

        if (interestsUser != null && !interestsUser.isEmpty()) {
            Page<NewsArticle> foundNewsArticles = newsArticleRepository
                    .findNewsArticlesByCategories(interestsUser, pageable);
            return foundNewsArticles.map(newsArticleMapper::toNewsArticleResponseDTO);
        } else {
            Page<NewsArticle> allNewsArticles = newsArticleRepository
                    .findAll(pageable);
            return allNewsArticles.map(newsArticleMapper::toNewsArticleResponseDTO);
        }
    }

    @Override
    public Page<NewsArticleResponseDTO> getFreshNewsArticles(Pageable pageable) {
        List<NewsArticle> articleList = newsArticleRepository.findAll();
        for (NewsArticle article : articleList) {
            if (!article.isFresh()) {
                articleList.remove(article);
            }
        }
        PageImpl<NewsArticle> page = new PageImpl<>(articleList, pageable, articleList.size());
        return page.map(newsArticleMapper::toNewsArticleResponseDTO);
    }

    @Override
    public Page<NewsArticleResponseDTO> getNewsArticleByTags(Set<String> tags, Pageable pageable) {
        Page<NewsArticle> pageArticleByTags = newsArticleRepository.findByTags(tags, pageable);
        return pageArticleByTags.map(newsArticleMapper::toNewsArticleResponseDTO);
    }

}
