package com.newshub.NewsHub.dto.articleDTO;

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
    private Integer likesCount;
    private Integer viewsCount;
    private Integer favoritesCount;
    private Boolean isPopular;

    public NewsArticleResponseDTO() {
    }

    public NewsArticleResponseDTO(Long id, String title, String summary, String content, String originalUrl, String author, String category, Set<String> tags, LocalDateTime publishedAt, LocalDateTime addedAt, Integer likesCount, Integer viewsCount, Integer favoritesCount, Boolean isPopular) {
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
        this.likesCount = likesCount;
        this.viewsCount = viewsCount;
        this.favoritesCount = favoritesCount;
        this.isPopular = isPopular;
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
                ", likesCount=" + likesCount +
                ", viewsCount=" + viewsCount +
                ", favoritesCount=" + favoritesCount +
                ", isPopular=" + isPopular +
                '}';
    }
}