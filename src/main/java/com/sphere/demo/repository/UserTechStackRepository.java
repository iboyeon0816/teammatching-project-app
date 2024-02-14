package com.sphere.demo.repository;

import com.sphere.demo.domain.mapping.UserTechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserTechStackRepository extends JpaRepository<UserTechStack, Long> {
    List<UserTechStack> findTechStacksByUserId(@Param("userId") Long userId);
}
