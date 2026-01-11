package com.newshub.NewsHub.model;

import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity(name = "Users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password")
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_status", nullable = false)
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ArticleLike> articleLikes = new HashSet<>();

    /**
     * Флаг, указывающий, что учетная запись не просрочена.
     * Используется Spring Security.
     */
    @Column(name = "account_non_expired", nullable = false)
    private Boolean accountNonExpired = true;

    /**
     * Флаг, указывающий, что учетная запись не заблокирована.
     * Используется Spring Security.
     */
    @Column(name = "account_non_locked", nullable = false)
    private Boolean accountNonLocked = true;

    /**
     * Флаг, указывающий, что учетные данные не просрочены.
     * Используется Spring Security.
     */
    @Column(name = "credentials_non_expired", nullable = false)
    private Boolean credentialsNonExpired = true;

    /**
     * Флаг, указывающий, что учетная запись активна.
     * Используется Spring Security.
     */
    @Column(name = "enabled", nullable = false)
    private Boolean enabled = true;

    /**
     * Токен для сброса пароля.
     * Генерируется при запросе сброса пароля.
     */
    @Column(name = "reset_password_token", length = 255)
    private String resetPasswordToken;

    /**
     * Срок действия токена сброса пароля.
     */
    @Column(name = "reset_password_token_expiry")
    private LocalDateTime resetPasswordTokenExpiry;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String username, String email, String passwordHash) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.status = UserStatus.ACTIVE;
        this.interests = new HashSet<>();;
        this.favoriteArticles = new HashSet<>();;
        this.readArticles = new HashSet<>();;
        this.userScore = 0;
        this.articleLikes = new HashSet<>();;
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.roles.add(Role.USER_ROLE);
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

        if (this.roles == null || this.roles.isEmpty()) {
            this.roles = new HashSet<>();
            this.roles.add(Role.USER_ROLE);
        }
    }

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired && status != UserStatus.DELETED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked && status != UserStatus.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled && status == UserStatus.ACTIVE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public @Nullable String getPassword() {
        return passwordHash;
    }

    public  String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
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

    public Set<ArticleLike> getArticleLikes() {
        return articleLikes;
    }

    public void setArticleLikes(Set<ArticleLike> articleLikes) {
        this.articleLikes = articleLikes;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public Boolean getCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public LocalDateTime getResetPasswordTokenExpiry() {
        return resetPasswordTokenExpiry;
    }

    public void setResetPasswordTokenExpiry(LocalDateTime resetPasswordTokenExpiry) {
        this.resetPasswordTokenExpiry = resetPasswordTokenExpiry;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * вспомогательные методы для Spring Security
     * метод генерации токена для сброса пароля
     */
    public void generateResetPasswordToken() {
        this.resetPasswordToken = UUID.randomUUID().toString();
        this.resetPasswordTokenExpiry = LocalDateTime.now().plusHours(24);
    }

    /**
     * метод очистки токена для сброса пароля
     */
    public void cleatResetPasswordToken() {
        this.resetPasswordToken = null;
        this.resetPasswordTokenExpiry = null;
    }

    /**
     * метод проверки для готовности сброса пароля
     * @return true/false
     */
    public boolean isResetPasswordTokenValid() {
        return this.resetPasswordToken != null &&
                this.resetPasswordTokenExpiry != null &&
                this.resetPasswordTokenExpiry.isAfter(LocalDateTime.now());
    }

    /**
     * метод добавления роли для пользователя
     */
    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new HashSet<>();
            this.roles.add(role);
        }
    }

    /**
     * метод удаления роли у пользователя
     */
    public void removeRole(Role role) {
        if (this.roles != null) {
            this.roles.remove(role);
        }
    }

    /**
     * метод проверки пользователя на указанную роль.
     */
    public boolean hasRole(Role role) {
        return this.roles != null && this.roles.contains(role);
    }

    /**
     * метод проверки пользователя на права администратора.
     */
    public boolean isAdmin() {
        return hasRole(Role.ADMIN_ROLE);
    }

    /**
     * вспомогательные методы для полей
     * метод добавления пользовательского интереса к новостям
     * @param interest интерес в контексте новостей (спорт, политика и тд.)
     */
    public void addInterest(String interest) {
        if (this.interests == null) {
            this.interests = new HashSet<>();
        }
        this.interests.add(interest);
    }

    /**
     * метод удаления пользовательского интереса к новостям
     *
     * @param interest интерес в контексте новостей (спорт, политика и тд.)
     */
    public void removeInterest(String interest) {
        this.interests.remove(interest);
    }

    /**
     * булевский метод о существовании определенного интереса у пользователя
     * @param interest интерес в контексте новостей (спорт, политика и тд.)
     * @return true/false
     */
    public boolean hasInterest(String interest) {
        return this.interests != null &&
                interest != null &&
                this.interests.contains(interest);
    }

    /**
     * метод проверка о том, что статья добавлена в избранное
     * @param article новостная статья
     * @return true/false
     */
    public boolean hasInFavorites(NewsArticle article) {
        return this.favoriteArticles != null &&
                this.favoriteArticles.contains(article);
    }

    /**
     * метод для отметки о прочтении статьи
     * @param article новостная статья
     */
    public void markIsRead(NewsArticle article) {
        if (this.readArticles != null && this.readArticles.contains(article)) {
            this.readArticles.add(article);
            if (article.getReadBy() != null && article.getViewsCount() != null) {
                article.getReadBy().add(this);
                article.incrementViewsCount();
            }
        }
    }

    /**
     * булевский метод о существовании лайка от пользователя к определённой новостной статье
     * @param article новостная статья
     * @return true/false
     */
    public boolean isLikedArticle(NewsArticle article) {
        if (article == null) {
            return false;
        }

        return this.articleLikes
                .stream()
                .anyMatch(like -> like.getArticle().equals(article) && like.isLiked());
    }

    /**
     * метод получения лайка к статье
     *
     * @param article новостная статья
     * @return лайк на статью
     */
    public ArticleLike getArticleLike(NewsArticle article) {
        if (article == null) {
            return null;
        }
        return articleLikes
                .stream()
                .filter(articleLike -> articleLike.getArticle().equals(article) && articleLike.isLiked())
                .findFirst()
                .orElse(null);
    }

    /**
     * метод добавления лайка к статье
     * @param article новостная статья
     */
    public void addLikeToNewsArticle(NewsArticle article, ArticleLike like) {
        if (article == null || like == null) {
            return;
        }
        if (isLikedArticle(article)) {
            ArticleLike existingLike = getArticleLike(article);
            if (!existingLike.getLiked() && existingLike.equals(like)) {
                existingLike.restore();
                return;
            }
            return;
        }
        this.articleLikes.add(like);
    }

    /**
     * метод получения всех лайков пользователя
     * @return кол-во лайков
     */
    public int getCountLikes() {
        return (int) this.articleLikes
                .stream()
                .filter(ArticleLike::isLiked).count();
    }

    /**
     * метод получения списка лайкнутых статей
     * @return список новостных статей, которые были лайкнуты
     */
    public List<NewsArticle> getListNewsArticleLiked() {
        return articleLikes
                .stream()
                .filter(ArticleLike::isLiked)
                .map(ArticleLike::getArticle)
                .toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
