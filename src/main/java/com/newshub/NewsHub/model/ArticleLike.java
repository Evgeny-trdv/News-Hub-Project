package com.newshub.NewsHub.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Article_likes",
        uniqueConstraints = {@UniqueConstraint(
                columnNames = ("user_id, articles_id"),
                name = "uk_user_article_like")})
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

    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
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
