package com.shortlinkv1.ShortLink.repository.MyUser;

import com.shortlinkv1.ShortLink.entity.UserEntity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
