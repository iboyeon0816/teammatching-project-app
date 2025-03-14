package com.sphere.demo.repository;

import com.sphere.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByNickname(String nickname);

    boolean existsByUnivEmail(String univEmail);

    Optional<User> findByUnivEmail(String univEmail);
}
