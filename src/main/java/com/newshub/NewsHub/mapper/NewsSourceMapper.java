package com.newshub.NewsHub.mapper;

import com.newshub.NewsHub.dto.sourceDTO.NewsSourceRequestDTO;
import com.newshub.NewsHub.dto.sourceDTO.NewsSourceResponseDTO;
import com.newshub.NewsHub.model.NewsSource;
import org.springframework.stereotype.Component;

@Component
public class NewsSourceMapper {

    public NewsSource toNewsSourceEntity(NewsSourceRequestDTO responseDTO) {
        NewsSource newsSource = new NewsSource();

        newsSource.setName(responseDTO.getName());
        newsSource.setSlug(responseDTO.getSlug());
        newsSource.setDescription(responseDTO.getDescription());
        newsSource.setWebsiteUrl(responseDTO.getWebsiteUrl());
        newsSource.setLogoUrl(responseDTO.getLogoUrl());
        newsSource.setPrimaryCategory(responseDTO.getPrimaryCategory());

        if (responseDTO.getCategories() != null) {
            newsSource.setCategories(responseDTO.getCategories());
        }
        newsSource.setLanguage(responseDTO.getLanguage());
        newsSource.setCountry(responseDTO.getCountry());
        newsSource.setActive(responseDTO.getIsActive());
        newsSource.setTrustScore(responseDTO.getTrustScore());
        newsSource.setUpdateFrequency(responseDTO.getUpdateFrequency());
        return newsSource;
    }

    public NewsSourceResponseDTO toNewsSourceResponseDTO(NewsSource entity) {
        NewsSourceResponseDTO newsSourceResponseDTO = new NewsSourceResponseDTO();

        newsSourceResponseDTO.setId(entity.getId());
        newsSourceResponseDTO.setName(entity.getName());
        newsSourceResponseDTO.setSlug(entity.getSlug());
        newsSourceResponseDTO.setDescription(entity.getDescription());
        newsSourceResponseDTO.setWebsiteUrl(entity.getWebsiteUrl());
        newsSourceResponseDTO.setLogoUrl(entity.getLogoUrl());
        newsSourceResponseDTO.setPrimaryCategory(entity.getPrimaryCategory());

        if (entity.getCategories() != null) {
            newsSourceResponseDTO.setCategories(entity.getCategories());
        }
        newsSourceResponseDTO.setLanguage(entity.getLanguage());
        newsSourceResponseDTO.setCountry(entity.getCountry());
        newsSourceResponseDTO.setIsActive(entity.getActive());
        newsSourceResponseDTO.setLastParsedAt(entity.getLastParsedAt());
        newsSourceResponseDTO.setCreatedAt(entity.getCreatedAt());
        newsSourceResponseDTO.setUpdatedAt(entity.getUpdatedAt());
        newsSourceResponseDTO.setArticleCount(entity.getArticlesCount());
        newsSourceResponseDTO.setTrustScore(entity.getTrustScore());
        newsSourceResponseDTO.setUpdateFrequency(entity.getUpdateFrequency());
        return newsSourceResponseDTO;
    }
}
