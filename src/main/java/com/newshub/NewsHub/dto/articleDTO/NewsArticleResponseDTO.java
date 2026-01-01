package com.newshub.NewsHub.dto.articleDTO;

import com.newshub.NewsHub.model.NewsSource;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * DTO для предоставления новостной статьи
 */
public class NewsArticleResponseDTO {

    private Long id;
    private String title;
    private String summary;
    private String content;
    private String originalUrl;
    private String author;
    private String category;
    private Set<String> tags;
    private LocalDateTime publishedAt;
    private LocalDateTime addedAt;
    private Long sourceId;
    private String sourceName;
    private String sourceSlug;
    private Integer likesCount;
    private Integer viewsCount;
    private Integer favoritesCount;
    private String language;
    private Boolean isPopular;
    private Boolean isProcessed;
    private Boolean isFresh;
    private Boolean isRecent;
    private Integer readingTimeMinutes;

    public NewsArticleResponseDTO() {
    }

    public NewsArticleResponseDTO(Long id, String title, String summary, String content, String originalUrl, String author, String category, Set<String> tags, LocalDateTime publishedAt, LocalDateTime addedAt, Long sourceId, String sourceName, String sourceSlug, Integer likesCount, Integer viewsCount, Integer favoritesCount, String language, Boolean isPopular, Boolean isProcessed, Boolean isFresh, Boolean isRecent, Integer readingTimeMinutes) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.originalUrl = originalUrl;
        this.author = author;
        this.category = category;
        this.tags = tags;
        this.publishedAt = publishedAt;
        this.addedAt = addedAt;
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.sourceSlug = sourceSlug;
        this.likesCount = likesCount;
        this.viewsCount = viewsCount;
        this.favoritesCount = favoritesCount;
        this.language = language;
        this.isPopular = isPopular;
        this.isProcessed = isProcessed;
        this.isFresh = isFresh;
        this.isRecent = isRecent;
        this.readingTimeMinutes = readingTimeMinutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(LocalDateTime addedAt) {
        this.addedAt = addedAt;
    }

    public Integer getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(Integer likesCount) {
        this.likesCount = likesCount;
    }

    public Integer getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }

    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public Boolean getPopular() {
        return isPopular;
    }

    public void setPopular(Boolean popular) {
        isPopular = popular;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceSlug() {
        return sourceSlug;
    }

    public void setSourceSlug(String sourceSlug) {
        this.sourceSlug = sourceSlug;
    }

    public Boolean getProcessed() {
        return isProcessed;
    }

    public void setProcessed(Boolean processed) {
        isProcessed = processed;
    }

    public Boolean getFresh() {
        return isFresh;
    }

    public void setFresh(Boolean fresh) {
        isFresh = fresh;
    }

    public Boolean getRecent() {
        return isRecent;
    }

    public void setRecent(Boolean recent) {
        isRecent = recent;
    }

    public Integer getReadingTimeMinutes() {
        return readingTimeMinutes;
    }

    public void setReadingTimeMinutes(Integer readingTimeMinutes) {
        this.readingTimeMinutes = readingTimeMinutes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsArticleResponseDTO that = (NewsArticleResponseDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "NewsArticleResponseDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", tags=" + tags +
                ", publishedAt=" + publishedAt +
                ", addedAt=" + addedAt +
                ", source=" + sourceId + ", " + sourceName +
                ", likesCount=" + likesCount +
                ", viewsCount=" + viewsCount +
                ", favoritesCount=" + favoritesCount +
                ", isPopular=" + isPopular +
                ", isProcessed=" + isProcessed +
                ", isFresh=" + isFresh +
                ", isRecent=" + isRecent +
                ", readingTimeMinutes=" + readingTimeMinutes +
                '}';
    }
}