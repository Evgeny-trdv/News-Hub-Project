package com.newshub.NewsHub.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Сущность, представляющая источник новостей.
 * Определяет откуда берутся статьи (BBC, TechCrunch, РИА Новости и т.д.).
 * Используется для классификации, фильтрации и определения доверия к контенту.
 */
@Entity
public class NewsSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "slug", unique = true)
    private String slug;

    @Column(name = "description")
    private String description;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "primary_category")
    private String primaryCategory;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "source_categories",
            joinColumns = @JoinColumn(name = "source_id")
    )
    @Column(name = "categories", length = 50)
    private Set<String> categories = new HashSet<String>();

    private String language = "ru";

    @Column(name = "country", length = 20)
    private String country;

    /**
     * Флаг активности источника.
     * Неактивные источники не парсятся и не отображаются.
     * Значение по умолчанию: true.
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /**
     * Дата и время последнего успешного парсинга/обновления.
     * Используется для мониторинга работы парсеров.
     */
    @Column(name = "last_parsed_at")
    private LocalDateTime lastParsedAt;

    /**
     * Связь One-to-Many со статьями.
     * Один источник может иметь множество статей.
     * CascadeType.ALL обеспечивает каскадное удаление.
     */
    @OneToMany(mappedBy = "source", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<NewsArticle> articles = new HashSet<>();

    /**
     * Дата и время создания записи об источнике в системе.
     * Заполняется автоматически. Не обновляется.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Дата и время последнего обновления записи.
     * Заполняется автоматически при каждом обновлении.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Количество статей от этого источника в системе.
     * Обновляется при добавлении/удалении статей.
     * Используется для статистики.
     */
    @Column(name = "articles_count")
    private Integer articlesCount = 0;

    /**
     * Коэффициент доверия к источнику (0-100).
     * Используется для ранжирования и фильтрации контента.
     * Источники с низким trustScore могут быть помечены как ненадежные.
     */
    @Column(name = "trust_score")
    private Integer trustScore = 50;

    /**
     * Частота обновления в минутах для автоматического парсинга.
     * null означает ручное обновление.
     * Используется планировщиком задач.
     */
    @Column(name = "update_frequency")
    private Integer updateFrequency;

    public NewsSource() {
    }

    public NewsSource(String name, String slug) {
        this.name = name;
        this.slug = slug;
        this.categories = new HashSet<>();
        this.articles = new HashSet<>();
        this.isActive = true;
        this.language = "ru";
        this.trustScore = 50;
        this.articlesCount = 0;
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.primaryCategory != null) {
            this.categories.add(this.primaryCategory);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getPrimaryCategory() {
        return primaryCategory;
    }

    public void setPrimaryCategory(String primaryCategory) {
        this.primaryCategory = primaryCategory;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public LocalDateTime getLastParsedAt() {
        return lastParsedAt;
    }

    public void setLastParsedAt(LocalDateTime lastParsedAt) {
        this.lastParsedAt = lastParsedAt;
    }

    public Set<NewsArticle> getArticles() {
        return articles;
    }

    public void setArticles(Set<NewsArticle> articles) {
        this.articles = articles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getArticlesCount() {
        return articlesCount;
    }

    public void setArticlesCount(Integer articlesCount) {
        this.articlesCount = articlesCount;
    }

    public Integer getTrustScore() {
        return trustScore;
    }

    public void setTrustScore(Integer trustScore) {
        this.trustScore = trustScore;
    }

    public Integer getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(Integer updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsSource that = (NewsSource) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "NewsSource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", description='" + description + '\'' +
                ", websiteUrl='" + websiteUrl + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", primaryCategory='" + primaryCategory + '\'' +
                ", categories=" + categories +
                ", language='" + language + '\'' +
                ", country='" + country + '\'' +
                ", isActive=" + isActive +
                ", lastParsedAt=" + lastParsedAt +
                ", articles=" + articles +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", articlesCount=" + articlesCount +
                ", trustScore=" + trustScore +
                ", updateFrequency=" + updateFrequency +
                '}';
    }

    /**
     * Вспомогательные методы
     */

    public void incrementArticlesCount() {
        if (this.articlesCount == null) {
            this.articlesCount = 0;
        }
        this.articlesCount++;
    }

    public boolean hasCategory(String category) {
        if (category == null) {
            return false;
        }
        return this.categories.contains(category) || this.primaryCategory.equals(category);
    }

    public void addCategory(String category) {
        if (this.categories == null) {
            this.categories = new HashSet<>();
        }
        this.categories.add(category);
    }

    public void removeCategory(String category) {
        if (this.categories != null && category != null) {
            this.categories.remove(category);
        }
    }

    public void updateLastParsedAt() {
        this.lastParsedAt = LocalDateTime.now();
    }
}
