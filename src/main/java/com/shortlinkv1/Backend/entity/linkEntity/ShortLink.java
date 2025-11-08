package com.shortlinkv1.Backend.entity.linkEntity;

import com.shortlinkv1.Backend.entity.tagEntity.Tag;
import com.shortlinkv1.Backend.entity.userEntity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "links")
@Getter
@Setter
@NoArgsConstructor
public class ShortLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

    @Column(name = "short_code", unique = true, nullable = false, length = 50)
    private String shortCode;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "click_link_count")
    private Integer clickLinkCount = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "link_tag",
        joinColumns = @JoinColumn(name = "link_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )

    private Set<Tag> tags = new HashSet<>();

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }


    public ShortLink(String originalUrl, String shortCode, User user) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }
}