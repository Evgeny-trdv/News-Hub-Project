package com.newshub.NewsHub.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "news_articles")
public class NewsArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Lob
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "original_url", length = 500)
    private String originalUrl;

    @Column(name = "author")
    private String author;

    @Column(name = "category")
    private String category;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "article_tag",
            joinColumns = @JoinColumn(name = "article_id")
    )
    @Column(name = "tags", length = 50)
    private Set<String> tags = new HashSet<>();

    @Column(name = "published_at")
    private LocalDateTime publishedAt;

    @Column(name = "added_at", updatable = false)
    private LocalDateTime addedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_id", nullable = false)
    private NewsSource source;

    @Column(name = "likes_count")
    private Integer likesCount = 0;

    @Column(name = "views_count")
    private Integer viewsCount = 0;

    @Column(name = "favorites_count")
    private Integer favoritesCount = 0;

    @Column(name = "is_popular")
    private Boolean isPopular = false;

    @Column(name = "is_processed")
    private Boolean isProcessed = false;

    @Column(name = "external_id")
    private String externalId;

    @Column(name = "language", length = 10)
    private String language;

    @ManyToMany(mappedBy = "favoriteArticles", fetch = FetchType.LAZY)
    private Set<User> favoritedBy = new HashSet<>();

    /**
     * Связь Many-to-Many с пользователями, прочитавшими статью.
     * Обратная сторона связи из User.readArticles.
     * Используется для формирования истории чтения.
     */
    @ManyToMany(mappedBy = "readArticles", fetch = FetchType.LAZY)
    private Set<User> readBy = new HashSet<>();

    public NewsArticle() {
    }

    public NewsArticle(String title, String category, LocalDateTime publishedAt, NewsSource source) {
        this.title = title;
        this.category = category;
        this.tags = new HashSet<>();
        this.publishedAt = publishedAt;
        this.source = source;
        this.likesCount = 0;
        this.viewsCount = 0;
        this.favoritesCount = 0;
        this.isPopular = false;
        this.isProcessed = false;
        this.favoritedBy = new HashSet<>();
        this.readBy = new HashSet<>();
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

    public Boolean getProcessed() {
        return isProcessed;
    }

    public void setProcessed(Boolean processed) {
        isProcessed = processed;
    }

    public Set<User> getFavoritedBy() {
        return favoritedBy;
    }

    public void setFavoritedBy(Set<User> favoritedBy) {
        this.favoritedBy = favoritedBy;
    }

    public NewsSource getSource() {
        return source;
    }

    public void setSource(NewsSource source) {
        this.source = source;
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

    public Set<User> getReadBy() {
        return readBy;
    }

    public void setReadBy(Set<User> readBy) {
        this.readBy = readBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsArticle that = (NewsArticle) o;
        return Objects.equals(id, that.id) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    @Override
    public String toString() {
        return "NewsArticle{" +
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
                ", source=" + source +
                ", likesCount=" + likesCount +
                ", viewsCount=" + viewsCount +
                ", favoritesCount=" + favoritesCount +
                ", isPopular=" + isPopular +
                ", isProcessed=" + isProcessed +
                ", externalId='" + externalId + '\'' +
                ", language='" + language + '\'' +
                ", favoritedBy=" + favoritedBy +
                ", readBy=" + readBy +
                '}';
    }

    /**
     * вспомогательные методы
     */
    public void incrementLikesCount() {
        if (this.likesCount == null) {
            this.likesCount = 0;
        }
        this.likesCount++;
    }

    public void incrementViewsCount() {
        if (this.viewsCount == null) {
            this.viewsCount = 0;
        }
        this.viewsCount++;
    }

    public void incrementFavoritesCount() {
        if (this.favoritesCount == null) {
            this.favoritesCount = 0;
        }
        this.favoritesCount++;
    }

    public boolean hasCategory(String category) {
        if (category == null || this.category == null) {
            return false;
        }
        return this.category.equals(category);
    }

    public boolean hasTag(String tag) {
        if (tag == null || this.tags == null) {
            return false;
        }
        return this.tags.contains(tag.toLowerCase());
    }

    public boolean isFresh() {
        if (this.publishedAt == null) {
            return false;
        }
        return this.publishedAt.isAfter(LocalDateTime.now().minusHours(24));
    }

    public boolean isRecent() {
        if (this.publishedAt == null) {
            return false;
        }
        return this.publishedAt.isAfter(LocalDateTime.now().minusDays(7));
    }

    public void addTag(String tag) {
        if (tag != null && !tag.isBlank()) {
            if (this.tags == null) {
                this.tags = new HashSet<>();
            }
            if (!this.hasTag(tag)) {
                this.tags.add(tag.toLowerCase());
            }
        }
    }

    public void removeTag(String tag) {
        if (tag != null && !tag.isBlank()) {
            if (this.hasTag(tag)) {
                this.tags.remove(tag.toLowerCase());
            }
        }
    }

    public void markIsPopular() {
        int popularity = 1000;
        if (this.likesCount != null && this.likesCount > popularity) {
            this.isPopular = true;
        }
    }
}
