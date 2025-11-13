package com.shortlinkv1.Backend.repository.ShortLink;

import com.shortlinkv1.Backend.entity.linkEntity.ShortLink;
import com.shortlinkv1.Backend.entity.userEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<ShortLink, Long> {
    Optional<ShortLink> findByShortCode(String shortCode);
    List<ShortLink> findByUser(User user);

    @Modifying
    @Query("DELETE FROM ShortLink s WHERE s.expiresAt IS NOT NULL AND s.expiresAt < :settime")
    void deleteByExpiresAtBefore(@Param("settime") LocalDateTime now);
}
