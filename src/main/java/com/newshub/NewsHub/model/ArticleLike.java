package com.newshub.NewsHub.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "Article_likes",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"user_id", "article_id"}
        ))
public class ArticleLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private NewsArticle article;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "liked")
    private Boolean liked = true;

    public ArticleLike() {
    }

    public ArticleLike(User user, NewsArticle article, LocalDateTime createdAt, LocalDateTime updatedAt, Boolean liked) {
        this.user = user;
        this.article = article;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.liked = liked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public NewsArticle getArticle() {
        return article;
    }

    public void setArticle(NewsArticle article) {
        this.article = article;
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

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleLike that = (ArticleLike) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ArticleLike{" +
                "id=" + id +
                ", user=" + user +
                ", article=" + article +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", liked=" + liked +
                '}';
    }

    /**
     * вспомогательные методы
     */
    public void cancel() {
        this.liked = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void restore() {
        this.liked = true;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isLiked() {
        return this.liked;
    }
}

