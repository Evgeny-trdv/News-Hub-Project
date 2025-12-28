package com.newshub.NewsHub.model;

import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status")
    private UserStatus status;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_interests")
    @Column(name = "interests")
    private Set<String> interests = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private Set<NewsArticle> favoriteArticles = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "read_history",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "article_id")
    )
    private Set<NewsArticle> readArticles = new HashSet<>();

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "user_score")
    private Integer userScore = 0;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = UserStatus.ACTIVE;
        this.interests = new HashSet<>();
        this.favoriteArticles = new HashSet<>();
        this.readArticles = new HashSet<>();
        this.userScore = 0;
    }

    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();

        if (this.interests != null && !this.interests.isEmpty()) {
            Set<String> formatedInterests = new HashSet<>();

            for (String interest : this.interests) {
                formatedInterests.add(StringUtils.capitalize(interest.trim().toLowerCase()));
            }

            this.interests.clear();
            this.interests.addAll(formatedInterests);
        }

        if (this.interests == null || this.interests.isEmpty()) {
            this.interests = new HashSet<>();
            this.interests.add("General");
        }

        if (this.status == null) {
            this.status = UserStatus.ACTIVE;
        }
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public Set<String> getInterests() {
        return interests;
    }

    public void setInterests(Set<String> interests) {
        this.interests = interests;
    }

    public Set<NewsArticle> getFavoriteArticles() {
        return favoriteArticles;
    }

    public void setFavoriteArticles(Set<NewsArticle> favoriteArticles) {
        this.favoriteArticles = favoriteArticles;
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

    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }

    public Set<NewsArticle> getReadArticles() {
        return readArticles;
    }

    public void setReadArticles(Set<NewsArticle> readArticles) {
        this.readArticles = readArticles;
    }

    /**
     * вспомогательные методы
     * @param interest интерес к контексте новостей (спорт, политика и тд.)
     */
    public void addCategory(String interest) {
        if (this.interests == null) {
            this.interests = new HashSet<>();
        }
        this.interests.add(interest);
    }

    public void removeCategory(String interest) {
        this.interests.remove(interest);
    }

    public boolean hasInterest(String interest) {
        return this.interests != null &&
                interest != null &&
                this.interests.contains(interest);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", status=" + status +
                ", interests=" + interests +
                ", favoriteArticles=" + favoriteArticles +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userScore=" + userScore +
                '}';
    }


}
