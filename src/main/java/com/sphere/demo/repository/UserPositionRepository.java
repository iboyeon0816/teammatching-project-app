package com.sphere.demo.repository;

import com.sphere.demo.domain.mapping.UserPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPositionRepository extends JpaRepository<UserPosition, Long> {
    List<UserPosition> findPositionsByUserId(@Param("userId") Long userId);
}
