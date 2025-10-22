package com.shortlinkv1.ShortLink.repository.ShortLink;

import com.shortlinkv1.ShortLink.entity.ShortLink;
import com.shortlinkv1.ShortLink.entity.userEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LinkRepository extends JpaRepository<ShortLink, Long> {
    Optional<ShortLink> findByShortCode(String shortCode);
    List<ShortLink> findByUser(User user);
}
