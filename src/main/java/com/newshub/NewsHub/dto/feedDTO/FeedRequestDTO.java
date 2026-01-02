package com.newshub.NewsHub.dto.feedDTO;

/**
 * DTO для передачи параметров запроса ленты новостей.
 * Содержит все настройки для формирования и фильтрации ленты.
 */
public class FeedRequestDTO {

    private Integer page = 0;
    private Integer size = 10;

    /**
     * для сортировки
     *
     */
    private String sortBy = "publishedAt";
    private String sortDirection = "DESC";

    /**
     * для фильтрации
     */
    private String category;
    private Long sourceId;
    private Boolean onlyFresh;
    private Boolean onlyPopular;
    private String language;

    public FeedRequestDTO() {
    }

    public FeedRequestDTO(Integer page, Integer size, String sortBy, String sortDirection, String category, Long sourceId, Boolean onlyFresh, Boolean onlyPopular, String language) {
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
        this.category = category;
        this.sourceId = sourceId;
        this.onlyFresh = onlyFresh;
        this.onlyPopular = onlyPopular;
        this.language = language;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public Boolean getOnlyFresh() {
        return onlyFresh;
    }

    public void setOnlyFresh(Boolean onlyFresh) {
        this.onlyFresh = onlyFresh;
    }

    public Boolean getOnlyPopular() {
        return onlyPopular;
    }

    public void setOnlyPopular(Boolean onlyPopular) {
        this.onlyPopular = onlyPopular;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
