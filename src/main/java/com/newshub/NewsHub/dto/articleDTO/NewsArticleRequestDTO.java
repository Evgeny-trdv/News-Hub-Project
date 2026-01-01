package com.newshub.NewsHub.dto.articleDTO;

import com.newshub.NewsHub.model.NewsSource;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

public class NewsArticleRequestDTO {

    private String title;
    private String summary;
    private String content;
    private String originalUrl;
    private String author;
    private String category;
    private Set<String> tags;
    private LocalDateTime publishedAt;
    private Long sourceId;
    private String externalId;
    private String language;

    public NewsArticleRequestDTO() {
    }

    public NewsArticleRequestDTO(Long sourceId, String externalId, String language, LocalDateTime publishedAt, Set<String> tags, String category, String author, String originalUrl, String content, String summary, String title) {
        this.sourceId = sourceId;
        this.externalId = externalId;
        this.language = language;
        this.publishedAt = publishedAt;
        this.tags = tags;
        this.category = category;
        this.author = author;
        this.originalUrl = originalUrl;
        this.content = content;
        this.summary = summary;
        this.title = title;
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

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsArticleRequestDTO that = (NewsArticleRequestDTO) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }

    @Override
    public String toString() {
        return "NewsArticleRequestDTO{" +
                "title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", content='" + content + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", author='" + author + '\'' +
                ", category='" + category + '\'' +
                ", tags=" + tags +
                ", publishedAt=" + publishedAt +
                '}';
    }
}
