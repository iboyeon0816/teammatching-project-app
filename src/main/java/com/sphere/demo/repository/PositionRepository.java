package com.sphere.demo.repository;

import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
