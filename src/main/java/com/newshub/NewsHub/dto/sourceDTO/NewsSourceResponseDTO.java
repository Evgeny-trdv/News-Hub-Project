package com.newshub.NewsHub.dto.sourceDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO для передачи данных об источнике клиенту.
 */
@Data
public class NewsSourceResponseDTO {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String websiteUrl;
    private String logoUrl;
    private String primaryCategory;
    private Set<String> categories;
    private String language;
    private String country;
    private Boolean isActive;
    private LocalDateTime lastParsedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer articleCount;
    private Integer trustScore;
    private Integer updateFrequency;
}
