package com.shortlinkv1.ShortLink.repository;

import com.shortlinkv1.ShortLink.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
