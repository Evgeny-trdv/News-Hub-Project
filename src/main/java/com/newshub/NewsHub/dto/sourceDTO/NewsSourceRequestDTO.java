package com.newshub.NewsHub.dto.sourceDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO для создания и обновления источника новостей.
 */
@Data
public class NewsSourceRequestDTO {

    @NotBlank(message = "Название источника обязательно")
    private String name;

    @NotBlank(message = "Slug источника обязателен")
    private String slug;

    private String description;
    private String websiteUrl;
    private String logoUrl;
    private String primaryCategory;
    private Set<String> categories;
    private String language;
    private String country;
    private Boolean isActive;
    private Integer trustScore;
    private Integer updateFrequency;

}
