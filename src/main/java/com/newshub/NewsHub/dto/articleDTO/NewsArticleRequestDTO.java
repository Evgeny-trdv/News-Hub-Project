package com.newshub.NewsHub.dto.articleDTO;

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

    public NewsArticleRequestDTO() {
    }

    public NewsArticleRequestDTO(String title, String summary, String content, String originalUrl, String author, String category, Set<String> tags, LocalDateTime publishedAt) {
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.originalUrl = originalUrl;
        this.author = author;
        this.category = category;
        this.tags = tags;
        this.publishedAt = publishedAt;
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
