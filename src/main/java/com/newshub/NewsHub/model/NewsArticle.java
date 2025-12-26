package com.newshub.NewsHub.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
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

    @Column(name = "likes_count")
    private Integer likesCount;

    @Column(name = "views_count")
    private Integer viewsCount;

    @Column(name = "favorites_count")
    private Integer favoritesCount;

    @Column(name = "is_popular")
    private Boolean isPopular = false;

    @Column(name = "is_processed")
    private Boolean isProcessed = false;

    @ManyToMany(mappedBy = "favoriteArticles", fetch = FetchType.LAZY)
    private Set<User> favoritedBy = new HashSet<>();

    public NewsArticle() {
    }

    public NewsArticle(String title, String content, String summary, String originalUrl, String author, String category) {
        this.title = title;
        this.content = content;
        this.summary = summary;
        this.originalUrl = originalUrl;
        this.author = author;
        this.category = category;
        this.favoritedBy = new HashSet<>();
        this.favoritesCount = 0;
        this.viewsCount = 0;
        this.likesCount = 0;
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
