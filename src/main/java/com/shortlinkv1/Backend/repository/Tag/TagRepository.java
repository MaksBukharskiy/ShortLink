package com.shortlinkv1.Backend.repository.Tag;

import com.shortlinkv1.Backend.entity.tagEntity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByName(String name);
}
