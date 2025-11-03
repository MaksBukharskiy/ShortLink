package com.shortlinkv1.Backend.entity.userEntity;

import com.shortlinkv1.Backend.models.dto.themeEnumModel.Theme;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "theme")
    private Theme userTheme = Theme.Light;


    public User() {
        this.createdAt = LocalDateTime.now();
    }

    public User(String email, String passwordHash) {
        this();
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public Theme getUserTheme() {
        return userTheme;
    }

    public void setUserTheme(Theme userTheme) {
        this.userTheme = userTheme;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
